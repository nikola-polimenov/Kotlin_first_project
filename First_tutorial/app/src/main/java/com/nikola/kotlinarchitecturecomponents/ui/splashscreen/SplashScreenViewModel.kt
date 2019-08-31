package com.nikola.kotlinarchitecturecomponents.ui.splashscreen

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nikola.kotlinarchitecturecomponents.network.NetworkRepository
import com.nikola.kotlinarchitecturecomponents.network.models.User
import com.nikola.kotlinarchitecturecomponents.room.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class SplashScreenViewModel(application: Application):AndroidViewModel(application) {
    val foundUser = MutableLiveData<User>()
    private val roomRepository: RoomRepository = RoomRepository(application)
    private val networkRepository: NetworkRepository = NetworkRepository()
    private val context = getApplication<Application>().applicationContext

    fun getUsers(username:String?, password:String?) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = networkRepository.getUsers(username, password)
                if (result.isSuccessful && !result.body().isNullOrEmpty()) {
                    foundUser.value = result.body()?.get(0)
                    Toast.makeText(context, "Login Successful, ${foundUser.value?.username}", Toast.LENGTH_SHORT).show()

                }else {
                    Toast.makeText(context, "Invalid details.", Toast.LENGTH_LONG).show()
                }
            }catch (e: Exception){
                Toast.makeText(context, "Server error: Please check your connection.", Toast.LENGTH_SHORT).show()
            }
        }

    }


}