package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.contacts

import android.app.Application
import android.content.Context

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nikola.kotlinarchitecturecomponents.network.NetworkRepository
import com.nikola.kotlinarchitecturecomponents.network.models.Profile
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.coroutines.CoroutineContext
import android.net.ConnectivityManager


class ContactsViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val networkRepository: NetworkRepository = NetworkRepository()
    var listOfContacts = MutableLiveData<List<ContactModel>>()
    private var list: MutableList<ContactModel> = ArrayList()
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Main

    fun getListOfContacts(username: String?) {
            launch(coroutineContext) {
                getListOfContactsPhaseOne(withContext(IO) {
                    networkRepository.getProfiles(username).body()?.get(0)
                })
            }

    }

    private suspend fun getListOfContactsPhaseOne(myProfile: Profile?) {
        var counter = 0
        list.clear()

        myProfile?.contacts?.forEach {
            getListOfContactsPhaseTwo(withContext(IO) {
                networkRepository.getProfiles(it).body()?.get(0)
            })
            counter++
        }
        when (counter){
            myProfile?.contacts?.size -> {
                listOfContacts.value = list // I want to put this in an if block to check if the list is unchanged. But it causes some unexpected errors.
            }
        }
    }

    private fun getListOfContactsPhaseTwo(contact: Profile?) {
        val foundContact = ContactModel(contact?.nickname, contact?.picture, contact?.status, contact?.username)
        list.add(foundContact)

    }


}