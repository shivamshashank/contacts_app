package com.example.contactsapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contactsapp.R
import com.example.contactsapp.model.SingleContact
import com.example.contactsapp.databinding.FragmentContactInfoBinding

class ContactInfoFragment : Fragment(R.layout.fragment_contact_info) {
    private lateinit var binding: FragmentContactInfoBinding

    private val _singleContact: ContactInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactInfoBinding.inflate(inflater)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val singleContact: SingleContact = _singleContact.singleContactArgs

        binding.apply {
            idTextView.text = "Id : ${singleContact.id}"
            firstNameTextView.text = "First Name : ${singleContact.first_name}"
            lastNameTextView.text = "Last Name : ${singleContact.last_name}"
            phoneTextView.text = "Phone : ${singleContact.phone}"
            emailTextView.text = "Email : ${singleContact.email}"

            sendMessageButton.setOnClickListener {
                findNavController().navigate(
                    ContactInfoFragmentDirections.actionContactInfoFragmentToSendMessageFragment(
                        singleContact.phone,
                    )
                )
            }
        }
    }
}