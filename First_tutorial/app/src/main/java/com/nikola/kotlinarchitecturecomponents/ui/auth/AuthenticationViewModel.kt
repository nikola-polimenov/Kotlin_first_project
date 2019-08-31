package com.nikola.kotlinarchitecturecomponents.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AuthenticationViewModel(application: Application) : AndroidViewModel(application) {


    //Saved this function as a comment for potential future use.
    /*
    fun findAllUsers() {
        CoroutineScope(IO).launch {
            val result = roomRepository.getAllUsers()

            withContext(Main) {
                users.value = result
            }
        }
    }
    */


}
