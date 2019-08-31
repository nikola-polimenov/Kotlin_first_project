package com.nikola.kotlinarchitecturecomponents.ui.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.nikola.kotlinarchitecturecomponents.R
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {
lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register.setOnClickListener {
            if (input_username.text.isNotEmpty()
                && input_password.text.isNotEmpty()
                && input_password.text.toString() == input_confirm_password.text.toString()) {

                registerViewModel.createUser(input_username.text.toString(), input_password.text.toString())

            }
        }
    }
}