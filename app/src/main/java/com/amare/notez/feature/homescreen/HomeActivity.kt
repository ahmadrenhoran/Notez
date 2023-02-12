package com.amare.notez.feature.homescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amare.notez.R
import com.amare.notez.databinding.ActivityHomeBinding
import com.amare.notez.feature.loginregister.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.centerText.text = intent.getStringExtra("LoginFragment")

    }
}