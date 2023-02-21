package com.amare.notez.core.data.repository

import com.amare.notez.core.domain.model.Note
import com.amare.notez.core.domain.model.Response
import com.amare.notez.core.domain.model.User
import com.amare.notez.core.domain.repository.*
import com.amare.notez.util.Utils
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseDatabase,
) : AuthRepository, NoteRepository {

    private val notesRef = db.reference.child("notes")

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
            val profileUpdates = userProfileChangeRequest {
                displayName = user.name
            }
            auth.currentUser!!.updateProfile(profileUpdates).await()
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

    override suspend fun getNotes(userId: String): NotesResponse {
        return try {
            Response.Success(ArrayList())
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    override suspend fun createNote(userId: String, note: Note): NoteResponse {
        return try {
            val newNoteRef = notesRef.child(userId).push()
            note.id = newNoteRef.key.toString()
            val map: MutableMap<String, Any> = HashMap()
            map.put("edited", ServerValue.TIMESTAMP)
            newNoteRef.setValue(note)
            newNoteRef.updateChildren(map).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    override suspend fun updateNote(userId: String, note: Note): NoteResponse {
        return try {
            val noteRef = notesRef.child(userId).child(note.id)

            val map: MutableMap<String, Any> = HashMap()
            map.put("edited", ServerValue.TIMESTAMP)
            noteRef.setValue(note)
            noteRef.updateChildren(map).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    override suspend fun deleteNoteById(userId: String, noteId: String): NoteResponse {
        return try {
            notesRef.child(userId).child(noteId).removeValue().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }


    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }

}

