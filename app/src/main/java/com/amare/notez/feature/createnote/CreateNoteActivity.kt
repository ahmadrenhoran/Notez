package com.amare.notez.feature.createnote


import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Build.VERSION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.amare.notez.R
import com.amare.notez.core.domain.model.Note
import com.amare.notez.core.domain.model.Response
import com.amare.notez.databinding.ActivityCreateNoteBinding
import com.amare.notez.util.Constants
import com.amare.notez.util.Utils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CreateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNoteBinding
    private val viewModel by viewModels<CreateNoteViewModel>()
    private var isOldNote = false
    private lateinit var note: Note

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val getNote = if (VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(Constants.PASSING_DATA_NOTE, Note::class.java)
        } else {
            intent.getParcelableExtra<Note>(Constants.PASSING_DATA_NOTE)
        }

        if (getNote != null) {
            getNote.apply {
                this@CreateNoteActivity.note = Note(id, title, note, edited, pinned)
            }
            lifecycleScope.launch {
                isOldNote = viewModel.isDataExist(Firebase.auth.currentUser?.uid!!, note.id)
                if (isOldNote) {
                    binding.apply {
                        if (note != null) {
                            titleEditText.setText(note.title)
                            noteEditText.setText(note.note)
                        }
                    }
                }
            }
            if (note.pinned) {
                binding.imgViewPin.setImageDrawable(getDrawable(R.drawable.fill_pin))
            } else {
                binding.imgViewPin.setImageDrawable(getDrawable(R.drawable.ic_pin))
            }
        } else {
            note = Note()
        }



        // action
        binding.apply {
            imgViewPin.setOnClickListener {
                if (note.pinned) {
                    note.pinned = false
                    binding.imgViewPin.setImageDrawable(getDrawable(R.drawable.ic_pin))
                } else {
                    note.pinned = true
                    binding.imgViewPin.setImageDrawable(getDrawable(R.drawable.fill_pin))
                }
            }
            imgViewBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            imgViewDelete.setOnClickListener {
                showDeleteConfirmationDialog()
            }
        }

        viewModel.deleteResponse.observe(this) { value ->
            when (value) {
                is Response.Success -> {
                    if (value.data == true) {
                        Utils.showToastText(this, "Success", Toast.LENGTH_LONG)
                        finish()
                    }
                }
                is Response.Error -> {
                    Utils.showToastText(this, value.e.toString(), Toast.LENGTH_LONG)
                }
                else -> {

                }
            }

        }


    }

    private fun createNote() {
        note.title = binding.titleEditText.text.toString()
        note.note = binding.noteEditText.text.toString()
        viewModel.createNote(Firebase.auth.currentUser?.uid!!, note)
        viewModel.noteResponse.observe(this) { value ->
            when (value) {
                is Response.Success -> {
                    Utils.showToastText(this, "Success", Toast.LENGTH_LONG)
                }
                is Response.Error -> {
                    Utils.showToastText(this, value.e.toString(), Toast.LENGTH_LONG)
                }
                else -> {

                }
            }

        }
    }

    private fun updateNote() {
        note.title = binding.titleEditText.text.toString()
        note.note = binding.noteEditText.text.toString()

        viewModel.updateNote(Firebase.auth.currentUser?.uid!!, note)
        viewModel.noteResponse.observe(this) { value ->
            when (value) {
                is Response.Success -> {
                    Utils.showToastText(this, "Success", Toast.LENGTH_LONG)
                }
                is Response.Error -> {
                    Utils.showToastText(this, value.e.toString(), Toast.LENGTH_LONG)
                }
                else -> {

                }
            }

        }
    }


    override fun onStop() {
        super.onStop()
        if (!isOldNote) {
            createNote()
        } else {
            binding.apply {
                // untuk mengecek apakah ada perubahan, jika tidak jangan di update
                if (!titleEditText.text.toString().trim()
                        .equals(note.title) || !noteEditText.text.toString().trim().equals(note.note)
                ) {
                    updateNote()
                }
            }

        }

    }

    fun showDeleteConfirmationDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure want to delete this note?")
        builder.setPositiveButton("Yes") { dialog, which ->
            viewModel.deleteNoteById(Firebase.auth.currentUser?.uid!!, note.id)
        }
        builder.setNegativeButton("No", null)

        builder.create().show()

    }




}