package com.amare.notez.feature.homescreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amare.notez.R
import com.amare.notez.core.domain.model.Note
import com.amare.notez.core.domain.model.User
import com.amare.notez.databinding.ActivityHomeBinding
import com.amare.notez.feature.createnote.CreateNoteActivity
import com.amare.notez.feature.loginregister.LoginRegisterViewModel
import com.amare.notez.feature.profile.ProfileActivity
import com.amare.notez.util.Constants
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
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = viewModel.user
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

    @SuppressLint("UseCompatLoadingForDrawables")
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

            imgViewTheme.setOnClickListener{
                showThemeDialog()
            }

            fabCreateNote.setOnClickListener {
                startActivity(Intent(this@HomeActivity, CreateNoteActivity::class.java))
            }

            when (AppCompatDelegate.getDefaultNightMode()) {
                AppCompatDelegate.MODE_NIGHT_YES -> {
                    binding.imgViewTheme.setImageDrawable(getDrawable(R.drawable.night))
                }
                AppCompatDelegate.MODE_NIGHT_NO -> {
                    binding.imgViewTheme.setImageDrawable(getDrawable(R.drawable.sun))
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun showThemeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val radioButtonLight = dialogView.findViewById<RadioButton>(R.id.radioButtonLight)
        val radioButtonDark = dialogView.findViewById<RadioButton>(R.id.radioButtonDark)
        val radioButtonAuto = dialogView.findViewById<RadioButton>(R.id.radioButtonAutoBattery)
        val radioButtonFollowSystem = dialogView.findViewById<RadioButton>(R.id.radioButtonFollowSystem)


        val themeString = getSharedPreferences(Constants.CURRENT_THEME, Context.MODE_PRIVATE).getString(Constants.CURRENT_THEME, Constants.FOLLOW_SYSTEM_MODE)!!
        when(themeString) {
            Constants.LIGHT_MODE -> radioButtonLight.isChecked = true
            Constants.DARK_MODE -> radioButtonDark.isChecked = true
            Constants.AUTO_BATTERY_MODE -> radioButtonAuto.isChecked = true
            Constants.FOLLOW_SYSTEM_MODE -> radioButtonFollowSystem.isChecked = true
        }

        // Tampilkan dialog
        AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Choose theme")
            .setPositiveButton("Yes") { dialog, _ ->
                if (radioButtonLight.isChecked) {
                    Utils.applyTheme(Constants.LIGHT_MODE, this)
                } else if (radioButtonDark.isChecked) {
                    Utils.applyTheme(Constants.DARK_MODE, this)
                } else if (radioButtonAuto.isChecked) {
                    Utils.applyTheme(Constants.AUTO_BATTERY_MODE, this)
                } else if (radioButtonFollowSystem.isChecked) {
                    Utils.applyTheme(Constants.FOLLOW_SYSTEM_MODE, this)
                }
                recreate()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }



}