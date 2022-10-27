package com.example.contactsapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.contactsapp.databinding.FragmentSendMessageBinding
import java.util.*

class SendMessageFragment : Fragment() {
    private lateinit var binding: FragmentSendMessageBinding

    private val _phone: SendMessageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSendMessageBinding.inflate(inflater)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val phone: String = _phone.phoneArgs

        binding.apply {
            val random: Int = Random().nextInt(999999)

            val randomNumber: String = String.format("%06d", random)

            editText.setText("Hi. Your OTP is : $randomNumber")
        }
    }
}