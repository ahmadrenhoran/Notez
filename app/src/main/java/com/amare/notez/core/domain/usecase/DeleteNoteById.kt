package com.amare.notez.core.domain.usecase

import com.amare.notez.core.domain.model.User
import com.amare.notez.core.domain.repository.NoteRepository

class DeleteNoteById(private val repository: NoteRepository) {

    suspend operator fun invoke(userId: String, noteId: String)  = repository.deleteNoteById(userId, noteId)

}