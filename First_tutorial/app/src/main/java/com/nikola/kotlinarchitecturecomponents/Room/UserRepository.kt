package com.nikola.kotlinarchitecturecomponents.room

import android.app.Application
import androidx.lifecycle.LiveData

class UserRepository(application: Application) {
    private val userDao: UserDAO?

    init {
        val db = UserDatabase.getInstance(application)
        userDao = db?.userDAO()
    }

    fun insertUser (user:UserEntity) {
        userDao?.insertUser(user)
    }

    fun deleteUser (user: UserEntity) {
        userDao?.deleteUser(user)
    }

    fun updateUser (user: UserEntity) {
        userDao?.updateUser(user)

    }

    fun findUserByName (username:String): LiveData<List<UserEntity>>? {
        return userDao?.findByUsername(username)
    }

}