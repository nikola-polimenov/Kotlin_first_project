package com.nikola.kotlinarchitecturecomponents.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikola.kotlinarchitecturecomponents.room.UserEntity
import com.nikola.kotlinarchitecturecomponents.room.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class AuthenticationViewModel(application: Application):AndroidViewModel(application) {
    private val userRepository: UserRepository = UserRepository(application)
    val foundUser = MutableLiveData<List<UserEntity>>()
    val users = MutableLiveData<List<UserEntity>>()

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

    fun findAllUsers() {
        CoroutineScope(Main).launch {
            users.value = getAllUsersFromDatabase()?.value
        }
    }

    private suspend fun getAllUsersFromDatabase(): LiveData<List<UserEntity>>? {
        return withContext(IO) {
            return@withContext userRepository.getAllUsers()
        }
    }

    fun findUserByUsername(username:String) {
            CoroutineScope(Main).launch {
                foundUser.value = getUserFromDatabase(username)?.value
            }

    }

    private suspend fun getUserFromDatabase(username: String): LiveData<List<UserEntity>>? {
        return withContext(IO) {
            return@withContext userRepository.findUserByName(username)
        }
    }


}
