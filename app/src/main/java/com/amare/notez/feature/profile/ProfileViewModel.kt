package com.amare.notez.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amare.notez.core.domain.model.Response
import com.amare.notez.core.domain.repository.RevokeAccessResponse
import com.amare.notez.core.domain.repository.SignOutResponse
import com.amare.notez.core.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val noteUseCases: NoteUseCases) : ViewModel() {

    private val _signOutResponse = MutableLiveData<SignOutResponse>(Response.Success(false))
    val signOutResponse: LiveData<SignOutResponse>
        get() = _signOutResponse

    private val _revokeResponse = MutableLiveData<RevokeAccessResponse>(Response.Success(false))
    val revokeResponse: LiveData<RevokeAccessResponse>
        get() = _revokeResponse

    fun signOut()  = viewModelScope.launch {
        _signOutResponse.value = noteUseCases.signOut.invoke()
    }

    fun revokeAccess()  = viewModelScope.launch {
        _revokeResponse.value = noteUseCases.revokeAccess.invoke()
    }
}