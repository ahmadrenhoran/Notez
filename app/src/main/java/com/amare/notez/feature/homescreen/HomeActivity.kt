package com.amare.notez.feature.homescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amare.notez.R
import com.amare.notez.databinding.ActivityHomeBinding
import com.amare.notez.feature.createnote.CreateNoteActivity
import com.amare.notez.feature.loginregister.LoginFragment
import com.amare.notez.feature.setting.SettingActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textViewUsername.text = String.format(
            resources.getString(R.string.welcome),
            Firebase.auth.currentUser?.displayName
        )
        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            imgViewSetting.setOnClickListener {
                startActivity(
                    Intent(
                        this@HomeActivity, SettingActivity::class.java
                    )
                )
            }

            fabCreateNote.setOnClickListener {
                startActivity(Intent(this@HomeActivity, CreateNoteActivity::class.java))
            }
        }
    }


}