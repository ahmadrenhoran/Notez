package com.amare.notez.core.domain.model

data class Note(
    var id: String = "",
    val title: String = "",
    val note: String = "",
    val edited: Long = 0L,
    val pinned: Boolean = false,
)
