package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.chatroom

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nikola.kotlinarchitecturecomponents.R
import com.nikola.kotlinarchitecturecomponents.network.models.MessageModel
import kotlinx.android.synthetic.main.layout_message_item.view.*

class MessageAdapter (var context:Context ,private val myUsername:String? ,private val clickListener:(MessageModel) -> Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<MessageModel> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MessageViewHolder(
            context,
            myUsername,
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_message_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MessageViewHolder -> {
                holder.bind(items[position], clickListener)
            }
        }
    }

    fun submitList(messageList: List<MessageModel>) {
        items = messageList
    }

    class MessageViewHolder constructor(var context: Context ,private var myUsername: String? ,itemView: View): RecyclerView.ViewHolder(itemView){
        private val messageText:TextView? = itemView.message_item
        private val container:LinearLayout = itemView.container_item_message

        fun bind(messageModel: MessageModel, clickListener: (MessageModel) -> Unit){

            messageText?.text = messageModel.messageText
            if (messageModel.sender == myUsername ) {
                messageText?.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
                messageText?.setTextColor(context.getColor(R.color.colorRed))
                container.gravity = Gravity.END
            } else {
                messageText?.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
                messageText?.setTextColor(context.getColor(R.color.colorPrimaryDark))
                container.gravity = Gravity.START
            }

            itemView.setOnClickListener {clickListener(messageModel)}

        }
    }


}