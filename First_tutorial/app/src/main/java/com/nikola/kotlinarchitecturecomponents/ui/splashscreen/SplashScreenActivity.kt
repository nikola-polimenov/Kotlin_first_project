package com.nikola.kotlinarchitecturecomponents.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.nikola.kotlinarchitecturecomponents.R
import com.nikola.kotlinarchitecturecomponents.ui.auth.AuthenticationActivity
import com.nikola.kotlinarchitecturecomponents.ui.main.MainActivity
import com.nikola.kotlinarchitecturecomponents.utility.PreferenceUtils

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 2000
    private lateinit var preferences: PreferenceUtils
    private lateinit var splashScreenViewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        preferences = PreferenceUtils(this)
        splashScreenViewModel = SplashScreenViewModel(application)

        Handler().postDelayed({
            if (preferences.getLoggedStatus() == 1) {
                splashScreenViewModel.getUsers(preferences.getRememberedUsername(), preferences.getRememberedPassword())
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, AuthenticationActivity::class.java))
            }

            finish()
        },SPLASH_TIME_OUT)
    }
}
