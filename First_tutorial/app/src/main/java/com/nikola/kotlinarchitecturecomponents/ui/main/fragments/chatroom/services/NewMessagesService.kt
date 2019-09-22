package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.chatroom.services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.nikola.kotlinarchitecturecomponents.network.NetworkRepository
import com.nikola.kotlinarchitecturecomponents.network.models.MessageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/*

/////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////
                TESTING BACKGROUND SERVICES
/////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////

 */
class NewMessagesService: IntentService("NewMessagesService") {
    private var listOfMessages:List<MessageModel>? = ArrayList()
    private lateinit var networkRepository: NetworkRepository

    override fun onHandleIntent(p0: Intent?) {
        networkRepository = NetworkRepository()
        CoroutineScope(Main).launch {
            p0?.extras
        }

    }

    private fun getMessages(chatRoomId:Int?) {
        CoroutineScope(IO).launch {
            try {
                val result = networkRepository.getMessages(chatRoomId)
                if (result.isSuccessful) {
                    withContext(Main) {
                        listOfMessages = result.body()
                    }
                    Log.e("Testing: List of messages", "${result.body()?.get(0)?.messageText}")
                    Log.e("Testing: ChatRoomApi", result.message())
                } else {
                    Log.e("Testing: ChatRoomApi", result.message())
                }
            } catch (e: Exception) {
                Log.e("Server error:", "${e.message}")
            }
        }
    }
}