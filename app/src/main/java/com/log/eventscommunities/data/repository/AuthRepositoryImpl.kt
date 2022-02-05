package com.log.eventscommunities.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.di.IoDispatcher
import com.log.eventscommunities.domain.repository.AuthRepository
import com.log.eventscommunities.domain.wrappers.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : AuthRepository {

    override suspend fun signIn(
        email: String,
        password: String,
    ): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading())
        val res = suspendCoroutine<Resource<FirebaseUser>> { coroutine ->
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    coroutine.resume(Resource.Success(it.result?.user))
                } else {
                    coroutine.resume(
                        Resource.Error(
                            it.exception?.localizedMessage ?: "Something go wrong!"
                        )
                    )
                }
            }
        }
        emit(res)
    }.flowOn(ioDispatcher)


    override suspend fun signOut() = auth.signOut()

    override suspend fun register(
        email: String,
        password: String,
    ): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading())
        val res = suspendCoroutine<Resource<FirebaseUser>> { coroutine ->
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    coroutine.resume(Resource.Success(it.result?.user))
                } else {
                    coroutine.resume(
                        Resource.Error(
                            it.exception?.localizedMessage ?: "Something go wrong!"
                        )
                    )
                }
            }
        }
        emit(res)
    }.flowOn(ioDispatcher)

    override fun fetchUser(): FirebaseUser? = auth.currentUser

    override fun isAuthenticated(): Boolean = auth.currentUser != null

    // TODO add auth flow as stream via listener
}