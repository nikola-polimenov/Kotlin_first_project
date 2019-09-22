package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.chatroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikola.kotlinarchitecturecomponents.R
import com.nikola.kotlinarchitecturecomponents.network.models.MessageModel
import com.nikola.kotlinarchitecturecomponents.ui.main.fragments.chatroom.services.NewMessagesService
import com.nikola.kotlinarchitecturecomponents.ui.main.fragments.contacts.TopSpacingItemDecoration
import com.nikola.kotlinarchitecturecomponents.utility.PreferenceUtils
import kotlinx.android.synthetic.main.fragment_chatroom.*

class ChatRoomFragment : Fragment() {
    private lateinit var preferences: PreferenceUtils
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var chatRoomViewModel: ChatRoomViewModel
    private var receiverUsername: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferences = PreferenceUtils(context)
        chatRoomViewModel = ViewModelProviders.of(this).get(ChatRoomViewModel::class.java)
        return inflater.inflate(R.layout.fragment_chatroom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiverUsername = arguments?.getString("contactUsername")
        Log.e("Testing: Data Transfer between fragments", "Receiver's username is: $receiverUsername")

        chatRoomViewModel.getChatRoom(preferences.getRememberedUsername(), receiverUsername)

        initRecyclerView()
        addDataSet()
        chatRoomViewModel.listOfMessages.observe(this, Observer {
            addDataSet()
            messageAdapter.notifyDataSetChanged()


        })

        send_message.setOnClickListener {
            sendMessage()
        }

    }

    private fun sendMessage() {
        chatRoomViewModel.sendMessage(input_message.text.toString(), preferences.getRememberedUsername(), receiverUsername, "")
    }

    private fun addDataSet() {
        val data = chatRoomViewModel.listOfMessages.value!!
        messageAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        recycler_view_chat_room.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingDecoration)
            messageAdapter = MessageAdapter(
                context,
                preferences.getRememberedUsername()
            ) { messageModel: MessageModel -> onMessageClicked(messageModel) }
            adapter = messageAdapter
        }
    }

    private fun onMessageClicked(messageModel: MessageModel) {
        Toast.makeText(context, "${messageModel.messageText} was clicked!", Toast.LENGTH_SHORT)
            .show()
    }

    /*
     private fun startBackgroundService() {
        if (chatRoomViewModel.chatRoomId != 0) {
            Log.e("Testing: ChatRoomId", "${chatRoomViewModel.chatRoomId}")
            val bundle = bundleOf("chatRoomId" to chatRoomViewModel.chatRoomId)
            Log.e("Testing: Bundle", "${bundle.get("chatRoomId")}")
            Intent(activity, NewMessagesService::class.java).also {

            }
        }
    }
     */

}