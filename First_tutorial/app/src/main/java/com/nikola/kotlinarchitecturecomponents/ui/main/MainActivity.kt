package com.nikola.kotlinarchitecturecomponents.ui.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.nikola.kotlinarchitecturecomponents.R
import com.nikola.kotlinarchitecturecomponents.ui.auth.AuthenticationActivity
import com.nikola.kotlinarchitecturecomponents.utility.PreferenceUtils

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var preferences: PreferenceUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        preferences = PreferenceUtils(this)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        /*
        test_logout.setOnClickListener {
            preferences.setRememberedUsername("")
            preferences.setRememberedPassword("")
            preferences.setLooged(0)
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        */
    }

}
