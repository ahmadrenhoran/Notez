package com.amare.notez.core.domain.repository

import com.amare.notez.core.domain.model.Response
import com.google.firebase.auth.AuthCredential

typealias SignInWithGoogleResponse = Response<Boolean>

interface AuthRepository {

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse
}