package com.nikola.kotlinarchitecturecomponents.room

import android.app.Application
import androidx.lifecycle.LiveData

class UserRepository(application: Application) {
    private val userDao: UserDAO?

    init {
        val db = UserDatabase.getInstance(application)
        userDao = db?.userDAO()
    }

    suspend fun insertUser(user:UserEntity) {
        userDao?.insertUser(user)
    }

    suspend fun deleteUser(user: UserEntity) {
        userDao?.deleteUser(user)
    }

    suspend fun updateUser(user: UserEntity) {
        userDao?.updateUser(user)

    }

    suspend fun findUserByName(username:String): LiveData<List<UserEntity>>? {
        return userDao?.findByUsername(username)
    }

    suspend fun getAllUsers(): LiveData<List<UserEntity>>? {
        return userDao?.getAllUsers()
    }

}