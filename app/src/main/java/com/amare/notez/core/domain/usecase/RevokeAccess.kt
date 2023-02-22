package com.amare.notez.core.domain.usecase

import com.amare.notez.core.domain.repository.ProfileRepository

class RevokeAccess(private val repository: ProfileRepository) {

    suspend fun invoke() = repository.revokeAccess()
}