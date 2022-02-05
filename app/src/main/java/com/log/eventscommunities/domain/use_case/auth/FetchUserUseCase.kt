package com.log.eventscommunities.domain.use_case.auth

import com.google.firebase.auth.FirebaseUser
import com.log.eventscommunities.domain.repository.AuthRepository
import javax.inject.Inject

class FetchUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): FirebaseUser? = repository.fetchUser()
}