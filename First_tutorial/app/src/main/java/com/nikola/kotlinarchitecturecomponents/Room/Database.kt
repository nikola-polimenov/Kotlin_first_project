package com.nikola.kotlinarchitecturecomponents.Room

import androidx.room.Database
import androidx.room.RoomDatabase

    @Database(entities = [UserEntity::class], version = 1)
    abstract class Database :RoomDatabase() {

        abstract fun userDAO():UserDAO
    }