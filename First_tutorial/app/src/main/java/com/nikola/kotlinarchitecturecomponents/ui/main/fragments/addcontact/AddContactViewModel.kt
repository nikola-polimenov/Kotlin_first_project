package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.addcontact

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nikola.kotlinarchitecturecomponents.network.NetworkRepository
import com.nikola.kotlinarchitecturecomponents.network.models.Profile
import com.nikola.kotlinarchitecturecomponents.network.models.ProfileContacts
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
    lateinit var myProfile: Profile

    override val coroutineContext: CoroutineContext
        get() = job + Main

    fun getListOfContacts(usernameOrNickname: String?) {
        list.clear()
        listOfContacts.value = ArrayList()
        getProfilesByUsername(usernameOrNickname)
        getProfilesByNickname(usernameOrNickname)

    }

    private fun getProfilesByUsername(username: String?) {
        CoroutineScope(IO).launch {
            try {
                val firstResult = networkRepository.getProfiles(username)
                if (firstResult.isSuccessful && !firstResult.body().isNullOrEmpty()) {
                    Log.i("Testing:", "${firstResult.body()?.get(0)?.username}")
                    withContext(Main) {
                        val foundUser = ContactModel(
                            firstResult.body()?.get(0)?.nickname,
                            firstResult.body()?.get(0)?.picture,
                            firstResult.body()?.get(0)?.status,
                            firstResult.body()?.get(0)?.username
                        )
                        list.add(foundUser)
                        listOfContacts.value = list
                    }
                } else {
                    Log.i("Testing:", "Found no such user!")
                }
            } catch (e: Exception) {
                Log.i("Server error:", "${e.message}")

            }
        }
    }

    private fun getProfilesByNickname(nickname: String?) {
        CoroutineScope(IO).launch {
            try {
                val secondResult = networkRepository.getProfilesByNickname(nickname)
                if (secondResult.isSuccessful && !secondResult.body().isNullOrEmpty()) {
                    Log.i("Testing:", "${secondResult.body()?.get(0)?.username}")
                    withContext(Main) {
                        val foundUser = ContactModel(
                            secondResult.body()?.get(0)?.nickname,
                            secondResult.body()?.get(0)?.picture,
                            secondResult.body()?.get(0)?.status,
                            secondResult.body()?.get(0)?.username
                        )
                        list.add(foundUser)
                        Log.i("Testing:", "$list")
                        listOfContacts.value = list
                    }
                } else {
                    Log.i("Testing:", "Found no such user!")
                }
            } catch (e: Exception) {
                Log.i("Server error:", "${e.message}")
            }
        }
    }

    fun addContact(myId: Int?, newContactList: ProfileContacts) {
        Log.i("Testing addContact parameters:", "$myId, $newContactList")
        launch(coroutineContext) {
            withContext(IO) {
                try {
                    val result = networkRepository.addContact(myId, newContactList)
                    if (result.isSuccessful) {
                        Log.i("Testing: AddContact functionality", "${result.body()?.contacts}")
                    } else {
                        Log.i("Testing: AddContact functionality", "Something went wrong!")

                    }
                } catch (e: Exception) {
                    Log.i("Server error:", "${e.message}")
                }
            }
        }
    }

    fun getMyProfile(username: String?) {
        myProfile = Profile()
        launch(coroutineContext) {
            withContext(IO) {
                try {
                    val result = networkRepository.getProfiles(username)
                    if (result.isSuccessful && !result.body().isNullOrEmpty()) {
                        withContext(Main) {
                            myProfile = Profile(
                                result.body()?.get(0)?.username,
                                result.body()?.get(0)?.picture,
                                result.body()?.get(0)?.nickname,
                                result.body()?.get(0)?.contacts,
                                result.body()?.get(0)?.status,
                                result.body()?.get(0)?.id
                            )
                        }
                    } else {
                        Log.i("Testing", "Something went wrong!")
                    }
                } catch (e: Exception) {
                    Log.i("Server error:", "${e.message}")
                }
            }
        }
    }


}