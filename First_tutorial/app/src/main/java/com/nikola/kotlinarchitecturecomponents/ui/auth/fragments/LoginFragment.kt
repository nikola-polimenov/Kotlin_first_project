package com.nikola.kotlinarchitecturecomponents.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.nikola.kotlinarchitecturecomponents.R
import com.nikola.kotlinarchitecturecomponents.ui.main.MainActivity
import com.nikola.kotlinarchitecturecomponents.utility.PreferenceUtils
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var preferences: PreferenceUtils

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        preferences = PreferenceUtils(context)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences.getRememberedUsername()?.let {
            input_username.setText(it)
        }

        open_register.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        login.setOnClickListener {
            loginViewModel.getUsers(input_username.text.toString(), input_password.text.toString())
            loginViewModel.foundUser.observe(this, Observer {
                if (input_username.text.toString() == loginViewModel.foundUser.value?.username
                    && input_password.text.toString() == loginViewModel.foundUser.value?.password) {

                    if (rememberDetailsChoice.isChecked) { preferences.setRememberedUsername(input_username.text.toString()) }
                    if (keepLogged.isChecked) {
                        preferences.setRememberedUsername(input_username.text.toString())
                        preferences.setRememberedPassword(input_password.text.toString())
                        preferences.setLooged(1)
                    }

                    startActivity(Intent(context, MainActivity::class.java))
                }
            })
        }
    }
}