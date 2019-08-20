package com.nikola.kotlinarchitecturecomponents.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.nikola.kotlinarchitecturecomponents.R
import com.nikola.kotlinarchitecturecomponents.room.UserEntity
import kotlinx.android.synthetic.main.activity_authentication.*

class AuthenticationActivity : AppCompatActivity() {
private lateinit var authViewModel: AuthenticationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        authViewModel = ViewModelProviders.of(this).get(AuthenticationViewModel::class.java)

        register.setOnClickListener {
            authViewModel.insertUser(UserEntity(id = 0, username = input_username.text.toString(),password = input_password.text.toString()))
            Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show()
        }


    }
}
