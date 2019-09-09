package com.nikola.kotlinarchitecturecomponents.ui.auth.fragments

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.nikola.kotlinarchitecturecomponents.network.NetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterViewModel (application: Application):AndroidViewModel(application) {
    private val networkRepository: NetworkRepository = NetworkRepository()
    private val context = getApplication<Application>().applicationContext

    fun createUser(username:String, password:String, picture: String, nickname: String) {
        CoroutineScope(Main).launch {
            try {
                val result = networkRepository.getUsers(username, password)
                if (result.isSuccessful && !result.body().isNullOrEmpty()) {
                    Toast.makeText(context, "Username is already taken.", Toast.LENGTH_SHORT).show()
                }else {
                    networkRepository.createUser(username, password)
                    Toast.makeText(context, "Registered successfully!", Toast.LENGTH_LONG).show()
                    createProfile(username, picture, nickname)
                }
            }catch (e: Exception){
                Toast.makeText(context, "Server error: Please check your connection.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createProfile(username: String,  picture: String, nickname: String) {
        CoroutineScope(Main).launch {
            try {
                networkRepository.createProfile(username, picture, nickname, ArrayList(), 1)
            }catch (e: Exception){
                Toast.makeText(context, "Server error: Please check your connection.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}