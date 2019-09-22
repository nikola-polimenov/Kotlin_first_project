package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nikola.kotlinarchitecturecomponents.R
import kotlinx.android.synthetic.main.layout_contact_list_item.view.*

class ContactsAdapter (private val clickListener:(ContactModel) -> Unit):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<ContactModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_contact_list_item,
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
            is ContactViewHolder -> {
                holder.bind(items[position], clickListener)
            }
        }
    }

    fun submitList(contactList: List<ContactModel>) {
        items = contactList
    }

    class ContactViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){

        private val contactNickname:TextView? = itemView.contact_nickname
        private val contactStatus:ImageView? = itemView.contact_status
        private val contactPicture:ImageView = itemView.contact_picture

        fun bind(contactModel: ContactModel, clickListener: (ContactModel) -> Unit){
            contactNickname?.text = contactModel.nickname
            if (contactModel.status == 1) {
                contactStatus?.setImageResource(R.drawable.status_online)
            } else {
                contactStatus?.setImageResource(R.drawable.status_offline)
            }
            itemView.setOnClickListener {clickListener(contactModel)}

            Glide.with(itemView.context).load(contactModel.picture).into(contactPicture)
        }
    }


}