package com.amare.notez.core.domain.repository

import com.amare.notez.core.domain.model.Note
import com.amare.notez.core.domain.model.Response
import com.google.firebase.auth.AuthCredential

typealias NoteResponse = Response<Boolean>
typealias NotesResponse = Response<List<Note>>

interface NoteRepository {
    suspend fun getNotes(userId: String): NotesResponse
    suspend fun createNote(userId: String, note: Note): NoteResponse
    suspend fun updateNote(userId: String, note: Note): NoteResponse
    suspend fun deleteNoteById(userId: String, noteId: String): NoteResponse
}