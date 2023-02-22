package com.amare.notez.core.domain.usecase

import com.amare.notez.core.domain.repository.ProfileRepository

class GetUser (private val profileRepository: ProfileRepository) {

    operator fun invoke() = profileRepository.user
}