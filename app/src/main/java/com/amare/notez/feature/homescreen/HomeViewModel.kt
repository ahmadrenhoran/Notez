package com.amare.notez.feature.homescreen

import androidx.lifecycle.ViewModel
import com.amare.notez.core.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val noteUseCases: NoteUseCases): ViewModel() {
    val user get() = noteUseCases.getUser.invoke()
}