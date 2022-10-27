package com.example.contactsapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsapp.R
import com.example.contactsapp.adapters.GetAllMessagesAdapter
import com.example.contactsapp.databinding.FragmentMessagesBinding
import com.example.contactsapp.utils.Resource
import com.example.contactsapp.view_model.GetMessagesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MessagesFragment : Fragment(R.layout.fragment_messages) {
    private lateinit var binding: FragmentMessagesBinding
    private lateinit var getAllMessagesAdapter: GetAllMessagesAdapter
    private val viewModel by viewModels<GetMessagesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessagesBinding.inflate(inflater)

        viewModel.getAllMessages()

        return binding.root
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMessagesRecyclerView()

        binding.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.getMessageStateFlow.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            messagesProgressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            messagesProgressBar.visibility = View.GONE

                            if (it.data == null) {
                                messagesFailureTextView.visibility = View.VISIBLE
                                messagesFailureTextView.text = "Data is Null"
                            } else {
                                if (it.data.messages.isEmpty()) {
                                    messagesFailureTextView.visibility = View.VISIBLE
                                    messagesFailureTextView.text = "Messages List is Empty"
                                } else {
                                    messagesRecyclerView.visibility = View.VISIBLE

                                    getAllMessagesAdapter.setMessagesList(it.data.messages)
                                    getAllMessagesAdapter.notifyDataSetChanged()
                                }
                            }
                        }
                        is Resource.Error -> {
                            messagesProgressBar.visibility = View.GONE
                            messagesFailureTextView.visibility = View.VISIBLE

                            messagesFailureTextView.text = it.message ?: "An Unknown Error Occurred"
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun setupMessagesRecyclerView() {
        getAllMessagesAdapter = GetAllMessagesAdapter()
        binding.messagesRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false,
            )
            adapter = getAllMessagesAdapter
        }
    }
}