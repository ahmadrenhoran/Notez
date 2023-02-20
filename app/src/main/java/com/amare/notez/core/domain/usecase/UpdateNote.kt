package com.amare.notez.core.domain.usecase

import com.amare.notez.core.domain.model.Note
import com.amare.notez.core.domain.repository.NoteRepository

class UpdateNote(private val repository: NoteRepository) {

    suspend operator fun invoke(userId: String, note: Note)  = repository.updateNote(userId, note)

}