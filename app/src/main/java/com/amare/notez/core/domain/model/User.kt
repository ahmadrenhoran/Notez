package com.amare.notez.core.domain.model

import android.provider.ContactsContract.CommonDataKinds.Email

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
)
