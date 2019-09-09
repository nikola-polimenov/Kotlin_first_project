package com.nikola.kotlinarchitecturecomponents.ui.auth.fragments

import android.app.Application

import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nikola.kotlinarchitecturecomponents.network.NetworkRepository
import com.nikola.kotlinarchitecturecomponents.network.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

import java.lang.Exception

class LoginViewModel (application: Application): AndroidViewModel(application) {
    var foundUser = MutableLiveData<User>()
    private val networkRepository:NetworkRepository = NetworkRepository()
    private val context = getApplication<Application>().applicationContext

    fun getUsers(username:String, password:String) {
        CoroutineScope(Main).launch {
            try {
                val result = networkRepository.getUsers(username, password)
                if (result.isSuccessful && !result.body().isNullOrEmpty()) {
                    foundUser.value = result.body()?.get(0)
                    //Toast.makeText(context, "Login Successful, ${foundUser.value?.username}", Toast.LENGTH_SHORT).show()
                }else {
                    foundUser.value = null
                    Toast.makeText(context, "Invalid details.", Toast.LENGTH_LONG).show()
                }
            }catch (e:Exception){
                Toast.makeText(context, "Server error: Please check your connection.", Toast.LENGTH_SHORT).show()
            }
        }

    }

}