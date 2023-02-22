package com.amare.notez.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.amare.notez.core.domain.model.User
import com.amare.notez.util.Constants.AUTO_BATTERY_MODE
import com.amare.notez.util.Constants.DARK_MODE
import com.amare.notez.util.Constants.FOLLOW_SYSTEM_MODE
import com.amare.notez.util.Constants.LIGHT_MODE
import com.google.firebase.auth.FirebaseUser
import java.text.DateFormat
import java.text.DateFormat.getDateTimeInstance
import java.util.*
import java.util.regex.Pattern

object Utils {

    @SuppressLint("CommitPrefEdits")
    fun applyTheme(themePreference: String, context: Context) {
        val preferences =
            context.getSharedPreferences(Constants.CURRENT_THEME, Context.MODE_PRIVATE).edit()
        when (themePreference) {
            LIGHT_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                preferences.putString(Constants.CURRENT_THEME, LIGHT_MODE)
            }
            DARK_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                preferences.putString(Constants.CURRENT_THEME, DARK_MODE)
            }
            AUTO_BATTERY_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                preferences.putString(Constants.CURRENT_THEME, AUTO_BATTERY_MODE)
            }
            FOLLOW_SYSTEM_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                preferences.putString(Constants.CURRENT_THEME, FOLLOW_SYSTEM_MODE)
            }

        }

        preferences.apply()
    }

    fun showToastText(context: Context, str: String, duration: Int) =
        Toast.makeText(context, str, duration).show()

    fun showLoading(loading: View, layout: View, show: Boolean) {
        if (show) {
            loading.visibility = View.VISIBLE
            layout.visibility = View.INVISIBLE
        } else {
            loading.visibility = View.GONE
            layout.visibility = View.VISIBLE
        }
    }

    fun toUser(user: FirebaseUser): User {
        user.apply {
            return User(uid, displayName!!, email!!)
        }
    }

    fun isEmailValid(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: String): Boolean {
        if (password.length < 8) return false
        // minimal ada satu huruf besar
        var pattern = Pattern.compile("(?=.*[A-Z])")
        if (!pattern.matcher(password).find()) return false

        // minimal ada satu angka
        pattern = Pattern.compile("(?=.*[0-9])")
        if (!pattern.matcher(password).find()) return false

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