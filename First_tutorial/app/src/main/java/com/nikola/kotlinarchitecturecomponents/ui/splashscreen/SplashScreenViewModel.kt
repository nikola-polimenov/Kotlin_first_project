package com.nikola.kotlinarchitecturecomponents.ui.splashscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nikola.kotlinarchitecturecomponents.room.UserEntity
import com.nikola.kotlinarchitecturecomponents.room.UserRepository


class SplashScreenViewModel(application: Application):AndroidViewModel(application) {
    private val userRepository:UserRepository = UserRepository(application)


}