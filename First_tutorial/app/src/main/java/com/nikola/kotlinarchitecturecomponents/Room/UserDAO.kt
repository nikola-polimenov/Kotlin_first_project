package com.nikola.kotlinarchitecturecomponents.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDAO {

    @Query("SELECT * FROM USERS WHERE username LIKE :username")
    suspend fun findByUsername(username:String): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user:UserEntity)

    @Delete
    suspend fun deleteUser(user:UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM USERS ORDER BY username ASC")
    suspend fun getAllUsers(): LiveData<List<UserEntity>>

}