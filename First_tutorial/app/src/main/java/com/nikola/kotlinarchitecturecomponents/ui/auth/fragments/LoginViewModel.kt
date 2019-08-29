package com.nikola.kotlinarchitecturecomponents.ui.auth.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nikola.kotlinarchitecturecomponents.network.UserApi
import com.nikola.kotlinarchitecturecomponents.network.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel (application: Application): AndroidViewModel(application) {
    val foundUser = MutableLiveData<List<User>>()

    fun getUsers(username: String, password: String) {
        UserApi.retrofitService.getUsers(username, password).enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("TAG", "Server error!")
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    Log.i("TAG", "Successfully logged in, ${response.body()?.get(0)?.username}")
                    foundUser.value = response.body()
                }
            }
        })
    }



}