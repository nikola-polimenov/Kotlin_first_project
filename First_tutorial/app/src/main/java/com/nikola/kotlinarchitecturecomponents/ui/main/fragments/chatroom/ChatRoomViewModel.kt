package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.chatroom

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nikola.kotlinarchitecturecomponents.network.NetworkRepository
import com.nikola.kotlinarchitecturecomponents.network.models.MessageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ChatRoomViewModel(application: Application) : AndroidViewModel(application) {
    private val networkRepository: NetworkRepository = NetworkRepository()
    var listOfMessages = MutableLiveData<List<MessageModel>>()
    private var chatRoomExistsCheck = 0
    var chatRoomId:Int? = 0

    fun getChatRoom(memberOne: String?, memberTwo: String?) {
        chatRoomExistsCheck = 0
        listOfMessages.value = ArrayList()
        CoroutineScope(Main).launch {
            withContext(IO) {
                getChatRoomPhaseOne(memberOne, memberTwo)
                getChatRoomPhaseTwo(memberOne, memberTwo)
                when(chatRoomExistsCheck) {
                    2 -> {
                        createChatRoom(memberOne, memberTwo)
                    }
                }
            }
        }

    }

    fun sendMessage(messageText: String?, sender:String?, receiver:String?, time:String?) {
        CoroutineScope(IO).launch {
            try {
                val result = networkRepository.sendMessage(chatRoomId, messageText, sender, receiver, time)
                if (result.isSuccessful) {
                    Log.e("Testing: Sending a message", "Message (${result.body()?.messageText}) sent successfully!")
                    getMessages(chatRoomId)
                }else{
                    Log.e("Testing: Sending a message", "Something went wrong!")
                }
            }catch (e:Exception) {
                Log.e("Server error:", "${e.message}")
            }
        }

    }

    private suspend fun createChatRoom(memberOne: String?, memberTwo: String?) {
        try {
            val result = networkRepository.createChatRoom(memberOne, memberTwo)
            if (result.isSuccessful) {
                chatRoomId = result.body()?.id
                getMessages(chatRoomId)
                Log.e("Testing: ChatRoomApi", result.message())
            } else {
                Log.e("Testing: ChatRoomApi", result.message())
            }
        } catch (e: Exception) {
            Log.e("Server error:", "${e.message}")
        }
    }

    private suspend fun getChatRoomPhaseOne(memberOne: String?, memberTwo: String?) {
        try {
            val result = networkRepository.getChatRoom(memberOne, memberTwo)
            if (result.isSuccessful && !result.body().isNullOrEmpty()) {
                withContext(Main) {
                    chatRoomId = result.body()?.get(0)?.id
                    getMessages(chatRoomId)
                    Log.e("Testing: ChatRoomApi", "Chat members are:${result.body()?.get(0)?.memberOne} and ${result.body()?.get(0)?.memberTwo}")
                }
            } else {
                Log.e("Testing: ChatRoomApi", "EmptyChatRoom.")
                chatRoomExistsCheck++
            }
        } catch (e: Exception) {
            Log.e("Server error:", "${e.message}")
        }
    }

    private suspend fun getChatRoomPhaseTwo(memberOne: String?, memberTwo: String?) {
        try {
            val result = networkRepository.getChatRoom(memberTwo, memberOne)
            if (result.isSuccessful && !result.body().isNullOrEmpty()) {
                withContext(Main) {
                    chatRoomId = result.body()?.get(0)?.id
                    getMessages(chatRoomId)
                    Log.e("Testing: ChatRoomApi", "Chat members are:${result.body()?.get(0)?.memberOne} and ${result.body()?.get(0)?.memberTwo}")
                }
            } else {
                Log.e("Testing: ChatRoomApi", "EmptyChatRoom.")
                chatRoomExistsCheck++
            }
        } catch (e: Exception) {
            Log.e("Server error:", "${e.message}")
        }
    }

    private suspend fun getMessages(chatRoomId:Int?) {
        try {
            val result = networkRepository.getMessages(chatRoomId)
            if (result.isSuccessful) {
                withContext(Main) {
                    listOfMessages.value = result.body()
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