package com.amare.notez.feature.loginregister

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amare.notez.core.domain.model.Response
import com.amare.notez.core.domain.repository.AuthRepository
import com.amare.notez.core.domain.repository.SignInWithGoogleResponse
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject constructor(
    private val repo: AuthRepository,
    val auth: FirebaseAuth,
    val signInClient: GoogleSignInClient
): ViewModel() {

    private val _googleResponse = MutableLiveData<SignInWithGoogleResponse>(Response.Success(false))
    val  googleResponse: LiveData<SignInWithGoogleResponse>
        get() = _googleResponse

    fun signInWithGoogle(googleCredential: AuthCredential) = viewModelScope.launch {
        _googleResponse.value = repo.firebaseSignInWithGoogle(googleCredential)
    }
}
