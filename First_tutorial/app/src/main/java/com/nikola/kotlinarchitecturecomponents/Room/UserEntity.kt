package com.nikola.kotlinarchitecturecomponents.room

import androidx.room.Entity
import androidx.room.PrimaryKey


    @Entity(tableName = "users")
    data class UserEntity(var username:String, var password:String) {
        @PrimaryKey (autoGenerate = true)
        var id:Int = 0
    }
