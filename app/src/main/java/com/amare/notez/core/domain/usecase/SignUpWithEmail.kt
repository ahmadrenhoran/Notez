package com.amare.notez.core.domain.usecase

import com.amare.notez.core.domain.model.User
import com.amare.notez.core.domain.repository.AuthRepository

class SignUpWithEmail(private val repository: AuthRepository) {

    suspend operator fun invoke(user: User, password: String)  = repository.firebaseSignUpWithEmail(user, password)

}