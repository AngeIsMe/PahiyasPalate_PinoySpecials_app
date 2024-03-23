package com.domondon.angeline.block4.p1.pahiyaspalate.fragment

import SharedPrefManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.domondon.angeline.block4.p1.pahiyaspalate.R
import com.domondon.angeline.block4.p1.pahiyaspalate.activity.Login
import com.domondon.angeline.block4.p1.pahiyaspalate.model.User

class ProfileFragment : Fragment() {

    lateinit var displayUsername: TextView
    lateinit var displayEmail: TextView
    lateinit var logoutbtn: Button

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
        displayEmail = view.findViewById(R.id.display_email)
        logoutbtn = view.findViewById(R.id.logout_btn)
        // Getting the current user from SharedPreferences
        val user = SharedPrefManager.getInstance(requireContext()).getUser()
        displayUsername.text = user.username
        displayEmail.text = user.email

        val logout = SharedPrefManager.getInstance(requireContext()).logout()
        logoutbtn.setOnClickListener {
            // Call the logout function
            logout

            // Start the login activity
            val intent = Intent(requireContext(), Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }



    }
}
