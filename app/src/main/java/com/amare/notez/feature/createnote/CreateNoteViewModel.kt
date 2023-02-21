package com.amare.notez.feature.createnote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amare.notez.core.domain.model.Note
import com.amare.notez.core.domain.model.Response
import com.amare.notez.core.domain.repository.NoteResponse
import com.amare.notez.core.domain.usecase.NoteUseCases
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(private val noteUseCases: NoteUseCases): ViewModel() {

    private val _noteResponse = MutableLiveData<NoteResponse>(Response.Success(false))
    val  noteResponse: LiveData<NoteResponse>
        get() = _noteResponse

    private val _deleteResponse = MutableLiveData<NoteResponse>(Response.Success(false))
    val  deleteResponse: LiveData<NoteResponse>
        get() = _deleteResponse

    private val _isNoteExist = MutableLiveData(false)
    val  isNoteExist: LiveData<Boolean>
        get() = _isNoteExist


    fun createNote(userId: String, note: Note) = viewModelScope.launch {
        _noteResponse.value = Response.Loading
        _noteResponse.value = noteUseCases.createNote(userId, note)
    }

    fun updateNote(userId: String, note: Note) = viewModelScope.launch {
        _noteResponse.value = Response.Loading
        _noteResponse.value = noteUseCases.updateNote(userId, note)
    }

    fun deleteNoteById(userId: String, noteId: String) = viewModelScope.launch {
        _deleteResponse.value = Response.Loading
        _deleteResponse.value = noteUseCases.deleteNoteById(userId, noteId)
    }

    suspend fun isDataExist(userId: String, noteId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val snapshot = Firebase.database.reference.child("notes").child(userId).child(noteId).get().await()
            snapshot.exists()
        } catch (e: Exception) {
            false
        }
    }

}