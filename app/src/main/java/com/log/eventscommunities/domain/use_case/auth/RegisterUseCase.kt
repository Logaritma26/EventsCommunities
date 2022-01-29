package com.log.eventscommunities.domain.use_case.auth

import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.domain.repository.AuthRepository
import com.log.eventscommunities.domain.wrappers.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): Flow<Resource<FirebaseUser>> = repository.register(email, password)
}