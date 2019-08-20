package com.nikola.kotlinarchitecturecomponents.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nikola.kotlinarchitecturecomponents.room.UserEntity
import com.nikola.kotlinarchitecturecomponents.room.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AuthenticationViewModel(application: Application):AndroidViewModel(application) {
    private val userRepository: UserRepository = UserRepository(application)

    fun insertUser(user:UserEntity) {
        CoroutineScope(IO).launch {
            userRepository.insertUser(user)
        }
    }
}