package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.contacts


import android.app.Application
import android.content.Context
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
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikola.kotlinarchitecturecomponents.R
import com.nikola.kotlinarchitecturecomponents.ui.main.MainActivity

import com.nikola.kotlinarchitecturecomponents.utility.PreferenceUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_contacts.*


class ContactsFragment : Fragment() {
    private lateinit var preferences: PreferenceUtils
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var contactsViewModel: ContactsViewModel
    private var listOfContacts:List<ContactModel>? = ArrayList()

    override fun onAttach(context: Context) {
        preferences = PreferenceUtils(context)
        contactsViewModel = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Contacts"
        contactsViewModel.getListOfContacts(preferences.getRememberedUsername())
        initRecyclerView()
        addDataSet()

        button_add_contact.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_contactsFragment_to_addContactFragment)
        }
        refreshRecyclerView()

    }

    private fun refreshRecyclerView() {
        contactsViewModel.listOfContacts.observe(this, Observer {
            listOfContacts = contactsViewModel.listOfContacts.value
            Log.i("Testing: listOfContacts (inside refreshRecyclerView() )", "${contactsViewModel.listOfContacts.value}")
            addDataSet()
            contactsAdapter.notifyDataSetChanged()

        })
    }

    private fun addDataSet() {
        Log.e("Testing: ListOfContacts (ContactsFragment)", "$listOfContacts")
        val data = listOfContacts!!
        contactsAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        Log.e("Testing: (insideContactFragment)", "initRecyclerView() was called!")
        recycler_view_contacts.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingDecoration = TopSpacingItemDecoration(20)
            addItemDecoration(topSpacingDecoration)
            contactsAdapter = ContactsAdapter { contactModel: ContactModel -> onContactClicked(contactModel) }
            adapter = contactsAdapter
        }
    }

    private fun onContactClicked(contactModel: ContactModel) {
        Log.i("Testing: RecyclerView onClickListener", "${contactModel.username} has been clicked!")
        val bundle = bundleOf("contactUsername" to contactModel.username)
        Log.e("Testing: Bundle", "${bundle.get("contactUsername")}")
        view?.let {
            findNavController().navigate(R.id.action_contactsFragment_to_chatRoomFragment, bundle)
        }
        activity?.title = "Chat with ${contactModel.nickname}"
    }

}