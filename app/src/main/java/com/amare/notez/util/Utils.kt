package com.amare.notez.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.amare.notez.core.domain.model.User
import com.google.firebase.auth.FirebaseUser
import java.text.DateFormat
import java.text.DateFormat.getDateTimeInstance
import java.util.*
import java.util.regex.Pattern

object Utils {

    private const val LIGHT_MODE = "Light"
    private const val DARK_MODE = "Dark"
    private const val AUTO_BATTERY_MODE = "Auto-battery"
    private const val FOLLOW_SYSTEM_MODE = "System"

    fun applyTheme(themePreference: String) {
        when(themePreference) {
            LIGHT_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            AUTO_BATTERY_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            FOLLOW_SYSTEM_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    fun showToastText(context: Context, str: String, duration: Int) = Toast.makeText(context, str, duration).show()

    fun showLoading(loading: View, layout: View, show: Boolean) {
        if (show) {
            loading.visibility = View.VISIBLE
            layout.visibility = View.INVISIBLE
        } else {
            loading.visibility = View.GONE
            layout.visibility = View.VISIBLE
        }
    }

    fun toUser(user: FirebaseUser) : User {
        user.apply {
            return User(uid, displayName!!, email!!)
        }
    }

    fun isEmailValid(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: String) : Boolean {
        if (password.length < 8) return false
        // minimal ada satu huruf besar
        var pattern = Pattern.compile("(?=.*[A-Z])")
        if(!pattern.matcher(password).find()) return false

        // minimal ada satu angka
        pattern = Pattern.compile("(?=.*[0-9])")
        if(!pattern.matcher(password).find()) return false

        return true
    }


    fun getTimeDate(timestamp: Long): String? {
        return try {
            val dateFormat: DateFormat = getDateTimeInstance()
            val netDate = Date(timestamp)
            dateFormat.format(netDate)
        } catch (e: Exception) {
            "date"
        }
    }





}