package com.amare.notez.core.domain.repository

import com.amare.notez.core.domain.model.Response
import com.amare.notez.core.domain.model.User
import com.google.firebase.auth.AuthCredential

typealias SignInWithGoogleResponse = Response<Boolean>
typealias SignInWithEmailResponse = Response<Boolean>

interface AuthRepository {

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse
    suspend fun firebaseSignInWithEmail(user: User, password: String): SignInWithEmailResponse
    suspend fun firebaseSignUpWithEmail(user: User, password: String): SignInWithEmailResponse
}