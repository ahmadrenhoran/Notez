package com.amare.notez.feature.loginregister

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amare.notez.core.domain.model.Response.Loading
import com.amare.notez.core.domain.model.Response.Success
import com.amare.notez.core.domain.model.User
import com.amare.notez.core.domain.repository.AuthRepository
import com.amare.notez.core.domain.repository.SignInWithEmailResponse
import com.amare.notez.core.domain.repository.SignInWithGoogleResponse
import com.amare.notez.core.domain.usecase.NoteUseCases
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    val signInClient: GoogleSignInClient
): ViewModel() {

    private val _googleResponse = MutableLiveData<SignInWithGoogleResponse>(Success(false))
    val  googleResponse: LiveData<SignInWithGoogleResponse>
        get() = _googleResponse

    private val _emailResponse = MutableLiveData<SignInWithEmailResponse>(Success(false))
    val  emailResponse: LiveData<SignInWithGoogleResponse>
        get() = _emailResponse

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        _googleResponse.value = Loading
        _googleResponse.value = noteUseCases.signInWithGoogle.invoke(googleCredential)
    }

    fun signInWithEmail(user: User, password: String) = viewModelScope.launch {
        _emailResponse.value = Loading
        _emailResponse.value = noteUseCases.signInWithEmail.invoke(user, password)
    }

    fun signUpWithEmail(user: User, password: String) = viewModelScope.launch {
        _emailResponse.value = Loading
        _emailResponse.value = noteUseCases.signUpWithEmail.invoke(user, password)
    }
}
