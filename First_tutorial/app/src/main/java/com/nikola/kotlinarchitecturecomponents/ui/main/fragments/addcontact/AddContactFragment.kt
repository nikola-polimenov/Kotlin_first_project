package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.addcontact

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikola.kotlinarchitecturecomponents.R
import com.nikola.kotlinarchitecturecomponents.ui.main.fragments.contacts.ContactModel
import com.nikola.kotlinarchitecturecomponents.ui.main.fragments.contacts.ContactsAdapter
import com.nikola.kotlinarchitecturecomponents.ui.main.fragments.contacts.TopSpacingItemDecoration
import com.nikola.kotlinarchitecturecomponents.utility.PreferenceUtils
import kotlinx.android.synthetic.main.fragment_addcontact.*

class AddContactFragment : Fragment() {
    private lateinit var addContactViewModel: AddContactViewModel
    private lateinit var preferences: PreferenceUtils
    private lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferences = PreferenceUtils(context)
        addContactViewModel = ViewModelProviders.of(this).get(AddContactViewModel::class.java)
        addContactViewModel.getMyProfile(preferences.getRememberedUsername())
        return inflater.inflate(R.layout.fragment_addcontact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        input_usernameOrNickname.addTextChangedListener(object : TextWatcher  {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val usernameOrNickname:String = p0.toString()
                addContactViewModel.getListOfContacts(usernameOrNickname)
            }

        })
        addContactViewModel.listOfContacts.value = ArrayList()
        initRecyclerView()
        addDataSet()

        addContactViewModel.listOfContacts.observe(this, Observer {
            addDataSet()
            contactsAdapter.notifyDataSetChanged()
        })
    }

    private fun onContactClicked(contactModel: ContactModel) {
        if (preferences.getRememberedUsername() != contactModel.username) {
            var contactCollisionChecker = 0
            addContactViewModel.myProfile.contacts?.forEach {
                if (it == contactModel.username) {
                    contactCollisionChecker++
                    Toast.makeText(context, "${contactModel.nickname} is already in your Contact list.", Toast.LENGTH_SHORT).show()
                }
            }
            if (contactCollisionChecker == 0) {
                addContactViewModel.addContact(addContactViewModel.myProfile.id, addContactViewModel.myProfile.contacts)
                Toast.makeText(context, "${contactModel.nickname} has been added to your Contact list.", Toast.LENGTH_SHORT).show()
            }
        }

        addContactViewModel.myProfile.contacts?.add(contactModel.username)
        Log.e("Testing my profile:", "${addContactViewModel.myProfile.contacts}")
    }

    private fun addDataSet() {
        val data = addContactViewModel.listOfContacts.value!!
        contactsAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        recycler_view_addcontact.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingDecoration = TopSpacingItemDecoration(20)
            addItemDecoration(topSpacingDecoration)
            contactsAdapter = ContactsAdapter { contactModel: ContactModel -> onContactClicked(contactModel) }
            adapter = contactsAdapter
        }
    }

}