package com.amare.notez.core.data.repository

import android.util.Log
import com.amare.notez.core.domain.model.Response
import com.amare.notez.core.domain.repository.AuthRepository
import com.amare.notez.core.domain.repository.SignInWithGoogleResponse
import com.amare.notez.util.Constants.SIGN_IN_REQUEST
import com.amare.notez.util.Constants.SIGN_UP_REQUEST
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @Named(SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest,
) : AuthRepository {

    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleResponse {
        return try {
            auth.signInWithCredential(googleCredential).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    suspend fun firebaseSignUpWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleResponse {
        return try {
            auth.signInWithCredential(googleCredential).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }


}