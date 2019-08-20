package com.nikola.kotlinarchitecturecomponents.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.nikola.kotlinarchitecturecomponents.R
import com.nikola.kotlinarchitecturecomponents.ui.auth.AuthenticationActivity

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 2000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed({
            startActivity(Intent(this, AuthenticationActivity::class.java))

            finish()
        },SPLASH_TIME_OUT)
    }
}
