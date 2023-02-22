package com.amare.notez.core.domain.usecase

import com.amare.notez.core.domain.repository.ProfileRepository

class SignOut(private val repository: ProfileRepository) {

    suspend operator fun invoke() = repository.signOut()
}