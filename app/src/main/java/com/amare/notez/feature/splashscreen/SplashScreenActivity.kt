package com.amare.notez.feature.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.amare.notez.R
import com.amare.notez.feature.loginregister.LoginRegisterActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        //startActivity(Intent(this@SplashScreenActivity, LoginRegisterActivity::class.java))
        //finish()

    }
}