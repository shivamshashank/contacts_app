package com.example.contactsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsapp.R
import com.example.contactsapp.adapters.ContactsAdapter
import com.example.contactsapp.databinding.FragmentContactsBinding
import com.example.contactsapp.utils.Resource
import com.example.contactsapp.view_model.ContactsViewModel
import kotlinx.coroutines.flow.collectLatest

class ContactsFragment : Fragment(R.layout.fragment_contacts) {
    private lateinit var binding: FragmentContactsBinding
    private lateinit var contactsAdapter: ContactsAdapter
    private val viewModel by viewModels<ContactsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater)

        viewModel.fetchContacts(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupContactsRecyclerView()

        lifecycleScope.launchWhenStarted {
            viewModel.contacts.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        binding.contactsRecyclerView.visibility = View.VISIBLE

                        contactsAdapter.differ.submitList(it.data)
                    }
                    is Resource.Error -> {
                        binding.contactsMessageTextView.visibility = View.VISIBLE
                        binding.contactsMessageTextView.text = it.message
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupContactsRecyclerView() {
        contactsAdapter = ContactsAdapter()
        binding.contactsRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false,
            )
            adapter = contactsAdapter
        }
    }
}