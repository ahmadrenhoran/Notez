package com.amare.notez.feature.loginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.amare.notez.R
import com.amare.notez.feature.homescreen.HomeActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_login_register)
        getSupportActionBar()?.hide();
//        val fragmentManager = supportFragmentManager
//        val loginFragment = LoginFragment()
//        fragmentManager.beginTransaction().add(R.id.login_register_frame, loginFragment, LoginFragment::class.java.simpleName).commit()

    }

    override fun onStart() {
        // if user already logged in
        if (Firebase.auth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        super.onStart()
    }


}