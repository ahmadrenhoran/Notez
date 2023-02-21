package com.amare.notez.feature.homescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amare.notez.R
import com.amare.notez.core.domain.model.Note
import com.amare.notez.core.domain.model.User
import com.amare.notez.databinding.ActivityHomeBinding
import com.amare.notez.feature.createnote.CreateNoteActivity
import com.amare.notez.feature.profile.ProfileActivity
import com.amare.notez.util.Utils
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: NoteFirebaseAdapter
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Firebase.auth.currentUser?.apply {
            user = User(uid, displayName ?: "", email ?: "")
        }
        binding.textViewUsername.text = String.format(
            resources.getString(R.string.welcome),
            user.name
        )

        setupAction()
    }

    override fun onStart() {
        super.onStart()
        adapter = NoteFirebaseAdapter(Firebase.database.reference.child("notes").child(user.id), this)
        adapter.startListening()
        binding.apply {
            listOtherNote.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            listOtherNote.adapter = adapter
        }
    }

    private fun setupAction() {
        binding.apply {
            val intent = Intent(
                this@HomeActivity, ProfileActivity::class.java
            )
            imgViewProfilePicture.setOnClickListener {
                startActivity(intent)
            }

            textViewUsername.setOnClickListener {
                startActivity(intent)
            }

            fabCreateNote.setOnClickListener {
                startActivity(Intent(this@HomeActivity, CreateNoteActivity::class.java))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


}