package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.contacts

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nikola.kotlinarchitecturecomponents.network.NetworkRepository
import com.nikola.kotlinarchitecturecomponents.network.models.Profile
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.coroutines.CoroutineContext


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
        Log.e("Testing: myProfile", "${myProfile?.nickname}")
        Log.e("Testing: Contacts's size", "${myProfile?.contacts?.size}")
        var counter:Int = 0
        if (list.size == myProfile?.contacts?.size) {
            list.clear()
        }
        myProfile?.contacts?.forEach {

            getListOfContactsPhaseTwo(withContext(IO) {
                networkRepository.getProfiles(it).body()?.get(0)
            })
            counter++
        }
        when (counter){
            myProfile?.contacts?.size -> {
                listOfContacts.value = list
            }
        }
    }

    private fun getListOfContactsPhaseTwo(contact: Profile?) {
        Log.e("Testing: contact", "${contact?.nickname}")
        val foundContact = ContactModel(contact?.nickname, contact?.picture, contact?.status)
        list.add(foundContact)

    }


}