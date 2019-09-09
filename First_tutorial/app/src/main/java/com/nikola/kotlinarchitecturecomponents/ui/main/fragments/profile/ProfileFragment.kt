package com.nikola.kotlinarchitecturecomponents.ui.main.fragments.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nikola.kotlinarchitecturecomponents.R
import com.nikola.kotlinarchitecturecomponents.utility.PreferenceUtils
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var preferences: PreferenceUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferences = PreferenceUtils(context)
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.getMyProfile(preferences.getRememberedUsername())
        profileViewModel.myProfile.observe(this, Observer {
            display_username.text = it.username
            edit_nickname.setText(it.nickname)
            edit_picture.setText(it.picture)
            edit_status.isChecked = it.status == 1
        })

        submit_changes.setOnClickListener {
            Log.i("MyProfile:", "id:${profileViewModel.myProfile.value?.id}")
            profileViewModel.editMyProfile(
                profileViewModel.myProfile.value?.id,
                edit_picture.text.toString(),
                edit_nickname.text.toString(),
                if (edit_status.isChecked) {
                    1
                } else 0
            )
        }
    }

}