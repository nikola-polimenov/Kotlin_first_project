package com.nikola.kotlinarchitecturecomponents.room

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UserRepository(application: Application) {
    private val userDAO: UserDAO?

    init {
        val db = UserDatabase.getInstance(application)
        userDAO = db?.userDAO()
    }

    fun insertUser (user:UserEntity) {
        CoroutineScope(IO).launch {
            userDAO?.insertUser(user)
        }
    }

    fun deleteUser (user: UserEntity) {
        CoroutineScope(IO).launch {
            userDAO?.deleteUser(user)
        }
    }

    fun updateUser (user: UserEntity) {
        CoroutineScope(IO).launch {
            userDAO?.updateUser(user)
        }
    }

    fun findUserByName (username:String): LiveData<List<UserEntity>> {
        CoroutineScope(IO).launch {
            val foundUser = userDAO?.findByUsername(username)
        }

    }




}