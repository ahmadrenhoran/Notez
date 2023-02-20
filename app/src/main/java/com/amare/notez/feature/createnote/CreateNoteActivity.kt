package com.amare.notez.feature.createnote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amare.notez.R
import com.amare.notez.databinding.ActivityCreateNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}