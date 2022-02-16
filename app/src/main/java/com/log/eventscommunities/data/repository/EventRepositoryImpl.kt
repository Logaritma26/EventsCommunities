package com.log.eventscommunities.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.log.eventscommunities.data.manager.FirebaseFunctionManager
import com.log.eventscommunities.di.IoDispatcher
import com.log.eventscommunities.domain.model.*
import com.log.eventscommunities.domain.repository.EventRepository
import com.log.eventscommunities.domain.wrappers.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val functionManager: FirebaseFunctionManager,
    private val fireStore: FirebaseFirestore,
) : EventRepository {

    override suspend fun postEvent(
        data: HashMap<String, *>
    ): Flow<Resource<FunctionResponse>> = flow {
        emit(Resource.Loading())
        val res = functionManager.fireFunction(
            functionName = "postEvent",
            data = data,
        )
        emit(res)
    }.flowOn(ioDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getEvents(): Flow<Resource<List<Event>>> {
        return callbackFlow {

            var eventsCollection: CollectionReference? = null

            try {
                eventsCollection = fireStore.collection("events")
            } catch (e: Throwable) {
                close(e)
            }

            val subscription = eventsCollection?.addSnapshotListener { snapshot, _ ->
                if (snapshot == null) {
                    return@addSnapshotListener
                }
                try {
                    val events = snapshot.documents.map {
                        val organizer = it.data!!["organizer"] as HashMap<*, *>
                        val attendeeList = it.data!!["attendants"] as List<HashMap<*, *>>
                        return@map Event(
                            id = it.data!!["id"] as String,
                            title = it.data!!["title"] as String,
                            description = it.data!!["description"] as String,
                            tag = (it.data!!["tag"] as Number).toInt(),
                            time = (it.data!!["time"] as Number).toLong(),
                            location = it.data!!["location"] as String,
                            organizer = Organizer(
                                organizer = organizer["id"] as String,
                                organizerName = organizer["name"] as String,
                            ),
                            attendants = attendeeList.map { map ->
                                Attendee(
                                    id = map["id"] as String,
                                    name = map["name"] as String,
                                )
                            },
                        )
                    }
                    trySend(Resource.Success(data = events))
                } catch (e: Throwable) {
                    Timber.d("Error getting fire docs: ${e.localizedMessage}")
                    Resource.Error<List<Event>>(exception = e)
                }
            }
            awaitClose { subscription?.remove() }
        }
    }

    override suspend fun getAttendingEvents(
        userId: String,
    ): Flow<Resource<UserDocument>> {
        return callbackFlow {
            val userDoc: DocumentReference = fireStore.collection("users").document(userId)

            val subscription = userDoc.addSnapshotListener { snapshot, e ->
                try {
                    if (e != null) {
                        Timber.d("Error getting fire docs: ${e.localizedMessage}")
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Timber.d("Current data: ${snapshot.data}")
                        val doc = snapshot.data!!
                        val userDocument = UserDocument(
                            attending = doc["attending"] as List<String>,
                            email = doc["email"] as String,
                            name = doc["name"] as String?,
                            uid = doc["uid"] as String,
                        )
                        trySend(Resource.Success(data = userDocument))
                    } else {
                        Timber.d("Data null")
                    }
                } catch (e: Throwable) {
                    Timber.d("Error getting fire docs: ${e.localizedMessage} - ${e.message} - ${e.toString()}")
                    Resource.Error<UserDocument>(exception = e)
                }
            }

            awaitClose { subscription.remove() }
        }
    }

    override suspend fun manageEvent(
        data: HashMap<String, *>,
        isAttending: Boolean,
    ): Flow<Resource<FunctionResponse>> = flow {
        emit(Resource.Loading())
        val function = if (isAttending) "attendEvent" else "leaveEvent"
        val res = functionManager.fireFunction(
            functionName = function,
            data = data,
        )
        emit(res)
    }.flowOn(ioDispatcher)

    override suspend fun watchEvent(
        eventId: String,
    ): Flow<Resource<Event>> {
        return callbackFlow {
            val eventDoc: DocumentReference = fireStore.collection("events").document(eventId)
            val subscription = eventDoc.addSnapshotListener { snapshot, e ->
                try {
                    if (e != null) {
                        Timber.d("Error getting fire docs: ${e.localizedMessage}")
                        return@addSnapshotListener
                    }
                    if (snapshot != null && snapshot.exists()) {
                        Timber.d("Current data: ${snapshot.data}")
                        val doc = snapshot.data!!

                        val organizer = doc["organizer"] as HashMap<*, *>
                        val attendeeList = doc["attendants"] as List<HashMap<*, *>>
                        val event = Event(
                            id = doc["id"] as String,
                            title = doc["title"] as String,
                            description = doc["description"] as String,
                            tag = (doc["tag"] as Number).toInt(),
                            time = (doc["time"] as Number).toLong(),
                            location = doc["location"] as String,
                            organizer = Organizer(
                                organizer = organizer["id"] as String,
                                organizerName = organizer["name"] as String,
                            ),
                            attendants = attendeeList.map { map ->
                                Attendee(
                                    id = map["id"] as String,
                                    name = map["name"] as String,
                                )
                            },
                        )
                        trySend(Resource.Success(data = event))
                    } else {
                        Timber.d("Data null")
                    }
                } catch (e: Throwable) {
                    Timber.d("Error getting fire docs: ${e.localizedMessage} - ${e.message} - ${e.toString()}")
                    Resource.Error<UserDocument>(exception = e)
                }
            }
            awaitClose { subscription.remove() }
        }
    }

}