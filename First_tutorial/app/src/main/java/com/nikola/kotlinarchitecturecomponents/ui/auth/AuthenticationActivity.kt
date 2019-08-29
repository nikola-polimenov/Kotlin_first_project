package com.nikola.kotlinarchitecturecomponents.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nikola.kotlinarchitecturecomponents.R
import com.nikola.kotlinarchitecturecomponents.room.UserEntity

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var authViewModel: AuthenticationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        authViewModel = ViewModelProviders.of(this).get(AuthenticationViewModel::class.java)
        authViewModel.findAllUsers()

        /*
        authBinding.register.setOnClickListener {
            authViewModel.insertUser(UserEntity(username = authBinding.inputUsername.text.toString(),password = authBinding.inputPassword.text.toString()))
            Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show()
        }


        authViewModel.users.observe(this, Observer {
            authBinding.test.text = it[0].username
        })
        */
    }
}
