package com.example.contactsapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.model.SingleContact
import com.example.contactsapp.databinding.ContactsRecyclerViewItemBinding
import com.example.contactsapp.fragments.ContactsFragmentDirections

@SuppressLint("SetTextI18n")
class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    inner class ContactsViewHolder(private var binding: ContactsRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(singleContact: SingleContact) {
            binding.apply {
                firstNameTextView.text = "First Name : ${singleContact.first_name}"
                lastNameTextView.text = "Last Name : ${singleContact.last_name}"

                cardView.setOnClickListener {
                    findNavController(it.findFragment()).navigate(
                        ContactsFragmentDirections.actionContactsFragmentToContactInfoFragment(singleContact)
                    )
                }
            }
        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<SingleContact>() {
        override fun areItemsTheSame(oldItem: SingleContact, newItem: SingleContact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SingleContact, newItem: SingleContact): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            ContactsRecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val singleContact = differ.currentList[position]
        holder.bind(singleContact)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}