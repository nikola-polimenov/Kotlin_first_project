package com.nikola.kotlinarchitecturecomponents.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.nikola.kotlinarchitecturecomponents.R

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var authViewModel: AuthenticationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        authViewModel = ViewModelProviders.of(this).get(AuthenticationViewModel::class.java)
    }
}
