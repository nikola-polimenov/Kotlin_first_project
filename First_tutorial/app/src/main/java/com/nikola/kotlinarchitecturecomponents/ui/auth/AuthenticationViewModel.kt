package com.nikola.kotlinarchitecturecomponents.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nikola.kotlinarchitecturecomponents.room.UserEntity
import com.nikola.kotlinarchitecturecomponents.room.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthenticationViewModel(application: Application):AndroidViewModel(application) {
    private val userRepository: UserRepository = UserRepository(application)

    fun insertUser(user:UserEntity) {
        CoroutineScope(IO).launch {
            userRepository.insertUser(user)
        }
    }

    fun deleteUser(user: UserEntity) {
        CoroutineScope(IO).launch {
            userRepository.deleteUser(user)
        }
    }

    fun updateUser(user: UserEntity) {
        CoroutineScope(IO).launch {
            userRepository.updateUser(user)
        }
    }

    //This is the method i really need help with, i don't have a clue how to get the LiveData out of the Coroutine
    /*
    fun findUserByUsername(username:String):LiveData<List<UserEntity>> {
            CoroutineScope(IO).launch {
                userRepository.findUserByName(username)
            }
        }
    */

}
