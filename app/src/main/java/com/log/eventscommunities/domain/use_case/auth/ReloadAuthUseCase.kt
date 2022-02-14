package com.log.eventscommunities.domain.use_case.auth

import com.log.eventscommunities.domain.repository.AuthRepository
import javax.inject.Inject

class ReloadAuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() {
        repository.reloadAuth()
    }
}