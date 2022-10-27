package com.example.contactsapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contactsapp.databinding.FragmentSendMessageBinding
import com.example.contactsapp.utils.Constants
import com.example.contactsapp.utils.Resource
import com.example.contactsapp.view_model.PostMessageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class SendMessageFragment : Fragment() {
    private lateinit var binding: FragmentSendMessageBinding

    private val viewModel: PostMessageViewModel by viewModels()

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

            val editTextMessage = "Hi. Your OTP is : $randomNumber"

            editTextOtp.setText(editTextMessage)

            sendButton.setOnClickListener {
                viewModel.postMessage(Constants.FROM_NUMBER, phone, editTextMessage)
            }

            lifecycleScope.launch {
                viewModel.postMessageStateFlow.collect {
                    when (it) {
                        is Resource.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            sendButton.visibility = View.GONE
                        }
                        is Resource.Success -> {
                            progressBar.visibility = View.GONE
                            sendButton.visibility = View.VISIBLE

                            val toastMessage: String =
                                if (it.data == null)
                                    "An Unknown Error Occurred"
                                else
                                    if (it.data.error_message == null)
                                        "Message Sent Successfully"
                                    else
                                        it.data.error_message.toString()

                            showToastMessageAndNavigateBack(toastMessage)
                        }
                        is Resource.Error -> {
                            progressBar.visibility = View.GONE
                            sendButton.visibility = View.VISIBLE

                            showToastMessageAndNavigateBack(
                                it.message ?: "An Unknown Error Occurred"
                            )
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun showToastMessageAndNavigateBack(toastMessage: String) {
        Toast.makeText(
            context,
            toastMessage,
            Toast.LENGTH_LONG,
        ).show()

        Handler(Looper.getMainLooper()).postDelayed(
            { findNavController().navigateUp() },
            2000,
        )

    }
}