package com.amare.notez.core.domain.usecase

import com.amare.notez.core.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential

class SignInWithGoogle(private val repository: AuthRepository) {

    suspend operator fun invoke(googleCredential: AuthCredential) = repository.firebaseSignInWithGoogle(googleCredential)
}