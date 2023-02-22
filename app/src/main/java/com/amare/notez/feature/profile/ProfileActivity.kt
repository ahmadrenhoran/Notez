package com.amare.notez.feature.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.amare.notez.core.domain.model.Response
import com.amare.notez.databinding.ActivityProfileBinding
import com.amare.notez.feature.loginregister.LoginRegisterActivity
import com.amare.notez.util.Utils

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            logoutButton.setOnClickListener {
                viewModel.signOut()
                viewModel.signOutResponse.observe(this@ProfileActivity) { value ->
                    when(value) {
                        is Response.Loading -> {}
                        is Response.Success -> {
                            if (value.data == true) {
                                startActivity(Intent(this@ProfileActivity, LoginRegisterActivity::class.java))
                                finish()
                            }
                        }
                        is Response.Error -> Utils.showToastText(this@ProfileActivity, value.e.toString(), Toast.LENGTH_LONG)
                    }
                }
            }
            revokeButton.setOnClickListener {
                viewModel.revokeAccess()
                viewModel.revokeResponse.observe(this@ProfileActivity) { value ->
                    when(value) {
                        is Response.Loading -> {}
                        is Response.Success -> {
                            if (value.data == true) {
                                startActivity(Intent(this@ProfileActivity, LoginRegisterActivity::class.java))
                                finish()
                            }
                        }
                        is Response.Error -> Utils.showToastText(this@ProfileActivity, value.e.toString(), Toast.LENGTH_LONG)
                    }
                }
            }
        }
    }
}