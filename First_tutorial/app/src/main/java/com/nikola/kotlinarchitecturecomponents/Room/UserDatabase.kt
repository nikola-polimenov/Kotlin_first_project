package com.nikola.kotlinarchitecturecomponents.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase :RoomDatabase() {
    abstract fun userDAO():UserDAO

    companion object {
        @Volatile
        var database: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            if (database == null) {
                synchronized(UserDatabase::class.java) {
                    if (database == null) {
                        database = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user database").build()
                    }
                }
            }

        return database
        }

    }


}