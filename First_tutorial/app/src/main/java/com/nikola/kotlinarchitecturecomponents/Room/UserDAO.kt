package com.nikola.kotlinarchitecturecomponents.Room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDAO {

    @Query("SELECT * FROM USERS WHERE username LIKE :username")
    fun findByUsername(username:String): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user:UserEntity)

    @Delete
    fun deleteUser(user:UserEntity)

    @Update
    fun updateUser(user: UserEntity)

    @Query("SELECT * FROM USERS ORDER BY username ASC")
    fun getAllUsers(): LiveData<List<UserEntity>>

}