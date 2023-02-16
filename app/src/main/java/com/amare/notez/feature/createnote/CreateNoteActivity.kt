package com.amare.notez.feature.createnote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amare.notez.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
    }
}