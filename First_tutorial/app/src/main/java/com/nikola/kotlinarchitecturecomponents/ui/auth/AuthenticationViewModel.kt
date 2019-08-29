package com.nikola.kotlinarchitecturecomponents.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nikola.kotlinarchitecturecomponents.room.UserEntity
import com.nikola.kotlinarchitecturecomponents.room.RoomRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class AuthenticationViewModel(application: Application) : AndroidViewModel(application) {
    private val roomRepository: RoomRepository = RoomRepository(application)
    val foundUser = MutableLiveData<List<UserEntity>>()
    val users = MutableLiveData<List<UserEntity>>()

    fun insertUser(user: UserEntity) {
        CoroutineScope(IO).launch {
            roomRepository.insertUser(user)
        }
    }

    fun deleteUser(user: UserEntity) {
        CoroutineScope(IO).launch {
            roomRepository.deleteUser(user)
        }
    }

    fun updateUser(user: UserEntity) {
        CoroutineScope(IO).launch {
            roomRepository.updateUser(user)
        }
    }

    fun findAllUsers() {
        CoroutineScope(IO).launch {
            val result = roomRepository.getAllUsers()

            withContext(Main) {
                users.value = result
            }
        }
    }

    fun findUserByUsername(username: String) {
        CoroutineScope(IO).launch {
            val result = roomRepository.findUserByName(username)

            withContext(Main) {
                foundUser.value = result
            }
        }

    }


}
