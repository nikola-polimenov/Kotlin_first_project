package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.addcontact

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nikola.kotlinarchitecturecomponents.network.NetworkRepository
import com.nikola.kotlinarchitecturecomponents.ui.main.fragments.contacts.ContactModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class AddContactViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {
    private val networkRepository: NetworkRepository = NetworkRepository()
    var listOfContacts = MutableLiveData<List<ContactModel>>()
    private var list: MutableList<ContactModel> = ArrayList()
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun getListOfContacts(usernameOrNickname: String?) {
        list.clear()
        listOfContacts.value = ArrayList()
        launch(coroutineContext) {
            launch(IO) {
                try {
                    val firstResult = networkRepository.getProfiles(usernameOrNickname)
                    if (firstResult.isSuccessful && !firstResult.body().isNullOrEmpty()) {
                        Log.e("Testing:", "${firstResult.body()?.get(0)?.username}")
                        withContext(Main) {
                            val foundUser = ContactModel(
                                firstResult.body()?.get(0)?.nickname,
                                firstResult.body()?.get(0)?.picture,
                                firstResult.body()?.get(0)?.status
                            )
                            list.add(foundUser)
                            listOfContacts.value = list
                        }
                    } else {
                        Log.e("Testing:", "Found no such user!")
                    }
                } catch (e: Exception) {
                    Log.e("Server error:", "${e.message}")

                }
            }
            launch(IO) {
                try {
                    val secondResult = networkRepository.getProfilesByNickname(usernameOrNickname)
                    if (secondResult.isSuccessful && !secondResult.body().isNullOrEmpty()) {
                        Log.e("Testing:", "${secondResult.body()?.get(0)?.username}")
                        withContext(Main) {
                            val foundUser = ContactModel(
                                secondResult.body()?.get(0)?.nickname,
                                secondResult.body()?.get(0)?.picture,
                                secondResult.body()?.get(0)?.status
                            )
                            list.add(foundUser)
                            Log.e("Testing:", "$list")
                            listOfContacts.value = list
                        }
                    } else {
                        Log.e("Testing:", "Found no such user!")
                    }
                } catch (e: Exception) {
                    Log.e("Server error:", "${e.message}")
                }
            }


        }
    }

}

/*
val secondResult = networkRepository.getProfilesByNickname(usernameOrNickname)
                    if (secondResult.isSuccessful && !secondResult.body().isNullOrEmpty()) {
                        Log.e("Testing:", "${secondResult.body()?.get(0)?.username}")
                        val foundUser = ContactModel(
                            secondResult.body()?.get(0)?.nickname,
                            secondResult.body()?.get(0)?.picture,
                            secondResult.body()?.get(0)?.status
                        )
                        list.add(foundUser)
                        Log.e("Testing:", "$list")
                    }
 */