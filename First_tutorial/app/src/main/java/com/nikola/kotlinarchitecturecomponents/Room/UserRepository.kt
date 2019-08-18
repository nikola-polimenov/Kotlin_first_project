package com.nikola.kotlinarchitecturecomponents.Room

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class UserRepository(application: Application) {
    private val userDAO: UserDAO?
    private val allUsers: LiveData<List<UserEntity>>?

    init {
        val db = UserDatabase.getInstance(application)
        userDAO = db?.userDAO()
        allUsers = userDAO?.getAllUsers()
    }

    fun insertUser(user:UserEntity) {
        InsertAsyncTask(userDAO!!).execute(user)
    }

    private class InsertAsyncTask(private val dao: UserDAO) :AsyncTask<UserEntity, Void, Void>() {
        override fun doInBackground(vararg p0: UserEntity): Void? {
            dao.insertUser(p0[0])
            return null
        }

    }

    fun deleteUser(user:UserEntity) {
        DeleteAsyncTask(userDAO!!).execute(user)
    }

    private class DeleteAsyncTask(private val dao: UserDAO) :AsyncTask<UserEntity, Void, Void>() {
        override fun doInBackground(vararg p0: UserEntity): Void? {
            dao.deleteUser(p0[0])
            return null
        }

    }

    fun updateUser(user: UserEntity) {
        UpdateAsyncTask(userDAO!!).execute(user)
    }

    private class UpdateAsyncTask(private val dao: UserDAO) :AsyncTask<UserEntity, Void, Void>() {
        override fun doInBackground(vararg p0: UserEntity): Void? {
            dao.updateUser(p0[0])
            return null
        }

    }

    /*
    fun findByUsername(username:String): LiveData<List<UserEntity>> {
        FindByUsernameAsyncTask(userDAO!!).execute(username)
    }

    private class FindByUsernameAsyncTask(private val dao: UserDAO) :AsyncTask<String, Void, Void>() {
        override fun doInBackground(vararg p0: String): Void? {
            dao.findByUsername(p0[0])
            return null
        }

    }
    */
}