package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.profile

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nikola.kotlinarchitecturecomponents.network.NetworkRepository
import com.nikola.kotlinarchitecturecomponents.network.models.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    private val networkRepository: NetworkRepository = NetworkRepository()
    var myProfile = MutableLiveData<Profile>()
    private val context = getApplication<Application>().applicationContext

    fun getMyProfile(my_username: String?){
        CoroutineScope(Main).launch {
            try {
                val result = networkRepository.getProfiles(my_username)
                if (result.isSuccessful && !result.body().isNullOrEmpty()) {
                    myProfile.value = result.body()?.get(0)

                }else{
                    myProfile.value = null
                }

            }catch (e: Exception) {

            }
        }
    }

    fun editMyProfile (id:Int?, picture:String?, nickname:String?, status: Int?) {
        CoroutineScope(Main).launch {
            try {
                val result = networkRepository.editProfile(id, picture, nickname, status)
                if (result.isSuccessful) {
                    Toast.makeText(context, "Edit successful!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Edit unsuccessful!", Toast.LENGTH_SHORT).show()
                }

            }catch (e:Exception) {
                Toast.makeText(context, "Server error!", Toast.LENGTH_SHORT).show()
                Log.e("Server error:", "${e.message}")
            }
        }
    }

}