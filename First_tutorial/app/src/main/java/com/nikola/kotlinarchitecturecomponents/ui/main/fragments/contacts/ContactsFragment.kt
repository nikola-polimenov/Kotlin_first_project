package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.contacts


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikola.kotlinarchitecturecomponents.R

import com.nikola.kotlinarchitecturecomponents.utility.PreferenceUtils
import kotlinx.android.synthetic.main.fragment_contacts.*


class ContactsFragment : Fragment() {
    private lateinit var preferences: PreferenceUtils
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var contactsViewModel: ContactsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferences = PreferenceUtils(context)
        contactsViewModel = ViewModelProviders.of(this).get(ContactsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactsViewModel.getListOfContacts(preferences.getRememberedUsername())
        initRecyclerView()
        addDataSet()

        button_add_contact.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_contactsFragment_to_addContactFragment)
        }

        contactsViewModel.listOfContacts.observe(this, Observer {
            Log.e("Testing: listOfContacts", "${contactsViewModel.listOfContacts.value}")
            addDataSet()
            contactsAdapter.notifyDataSetChanged()
        })

    }

    private fun addDataSet() {
        val data = contactsViewModel.listOfContacts.value!!
        contactsAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        recycler_view_contacts.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingDecoration = TopSpacingItemDecoration(20)
            addItemDecoration(topSpacingDecoration)
            contactsAdapter = ContactsAdapter()
            adapter = contactsAdapter
        }
    }

}