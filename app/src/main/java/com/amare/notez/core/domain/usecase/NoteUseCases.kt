package com.amare.notez.core.domain.usecase

data class NoteUseCases(
    val signInWithGoogle: SignInWithGoogle,
    val signInWithEmail: SignInWithEmail,
    val signUpWithEmail: SignUpWithEmail,
    val createNote: CreateNote,
    val updateNote: UpdateNote,
    val deleteNoteById: DeleteNoteById,
)
