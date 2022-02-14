package com.log.eventscommunities.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.log.eventscommunities.data.manager.FirebaseFunctionManager
import com.log.eventscommunities.di.IoDispatcher
import com.log.eventscommunities.domain.model.Event
import com.log.eventscommunities.domain.model.Organizer
import com.log.eventscommunities.domain.repository.HomeRepository
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

class HomeRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val functionManager: FirebaseFunctionManager,
    private val fireStore: FirebaseFirestore,
) : HomeRepository {

    override suspend fun postEvent(data: HashMap<String, *>): Flow<Resource<Any>> = flow {
        emit(Resource.Loading())
        val res2 = functionManager.fireFunction(
            functionName = "postEvent",
            data = data,
        )
        emit(res2)
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
                        return@map Event(
                            title = it.data!!["title"] as String,
                            description = it.data!!["description"] as String,
                            tag = (it.data!!["tag"] as Number).toInt(),
                            time = (it.data!!["time"] as Number).toLong(),
                            location = it.data!!["location"] as String,
                            organizer = Organizer(
                                organizer = organizer["id"] as String,
                                organizerName = organizer["name"] as String,
                            )
                        )
                    }
                    trySend(Resource.Success(data = events))
                } catch (e: Throwable) {
                    Timber.d("Error getting fire docs: ${e.localizedMessage}")
                }
            }
            awaitClose { subscription?.remove() }
        }
    }

}