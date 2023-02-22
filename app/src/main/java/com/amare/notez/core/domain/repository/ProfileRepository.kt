package com.amare.notez.core.domain.repository

import com.amare.notez.core.domain.model.Response
import com.amare.notez.core.domain.model.User

typealias SignOutResponse = Response<Boolean>
typealias RevokeAccessResponse = Response<Boolean>
typealias UpdateProfileResponse = Response<Boolean>

interface ProfileRepository {
    val user: User

    suspend fun signOut(): SignOutResponse
    suspend fun revokeAccess(): RevokeAccessResponse
}