package com.log.eventscommunities.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.domain.wrappers.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signIn(email: String, password: String): Flow<Resource<FirebaseUser>>

    suspend fun signOut()

    suspend fun register(email: String, password: String): Flow<Resource<FirebaseUser>>

    suspend fun fetchUser(): FirebaseUser

    fun isAuthenticated(): Boolean

}