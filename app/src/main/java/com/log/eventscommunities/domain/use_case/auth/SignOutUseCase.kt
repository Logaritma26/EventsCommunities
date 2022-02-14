package com.log.eventscommunities.domain.use_case.auth

import com.log.eventscommunities.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke() {
        repository.signOut()
    }
}