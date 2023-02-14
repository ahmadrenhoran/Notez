package com.amare.notez.core.data.repository

import com.amare.notez.core.domain.model.Response
import com.amare.notez.core.domain.model.User
import com.amare.notez.core.domain.repository.AuthRepository
import com.amare.notez.core.domain.repository.SignInWithGoogleResponse
import com.amare.notez.util.Utils
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseDatabase,
) : AuthRepository {

    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleResponse {
        return try {
            val authResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                addUserToDatabase()
            }

            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    override suspend fun firebaseSignInWithEmail(user: User, password: String): SignInWithGoogleResponse {
        return try {
            auth.signInWithEmailAndPassword(user.email, password).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    override suspend fun firebaseSignUpWithEmail(user: User, password: String): SignInWithGoogleResponse {
        return try {
            auth.createUserWithEmailAndPassword(user.email, password).await()
            addUserToDatabase()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    private suspend fun addUserToDatabase() {
        val usersRef = db.getReference("users")
        auth.currentUser?.apply {
            val user = Utils.toUser(this)
            usersRef.child(user.id).setValue(user).await()
        }
    }

}

