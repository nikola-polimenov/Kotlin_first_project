package com.nikola.kotlinarchitecturecomponents.Room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDAO {

    @Query("SELECT * FROM USERS WHERE username LIKE :username")
    fun findByUsername(username:String): LiveData<List<UserEntity>>

    @Insert
    fun insertUser(vararg user:UserEntity)

    @Delete
    fun deleteUser(user:UserEntity)

    @Update
    fun updateUser(vararg user: UserEntity)

}