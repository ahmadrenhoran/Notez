package com.amare.notez.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    var id: String = "",
    var title: String = "",
    var note: String = "",
    var edited: Long = 0L,
    var pinned: Boolean = false,
): Parcelable
