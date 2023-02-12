package com.amare.notez.feature.loginregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.amare.notez.R
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


}