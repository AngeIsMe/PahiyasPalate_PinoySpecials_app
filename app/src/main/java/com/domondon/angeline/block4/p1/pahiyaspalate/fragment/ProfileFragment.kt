package com.domondon.angeline.block4.p1.pahiyaspalate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.model.User

class ProfileFragment : Fragment() {

    lateinit var displayUsername: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayUsername = view.findViewById(R.id.display_username)
        // Getting the current user from SharedPreferences
        val user = SharedPrefManager.getInstance(requireContext()).getUser()
        displayUsername.text = user.username
    }
}
