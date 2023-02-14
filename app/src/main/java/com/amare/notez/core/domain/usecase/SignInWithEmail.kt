package com.amare.notez.core.domain.usecase

import com.amare.notez.core.domain.model.User
import com.amare.notez.core.domain.repository.AuthRepository

class SignInWithEmail(private val repository: AuthRepository) {

    suspend operator fun invoke(user: User, password: String)  = repository.firebaseSignInWithEmail(user, password)

}