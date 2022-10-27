package com.example.contactsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.databinding.SingleMessageRecyclerViewItemBinding
import com.example.contactsapp.model.get_message.Message

class GetAllMessagesAdapter : RecyclerView.Adapter<MyViewHolder>() {
    private val messagesList = ArrayList<Message>()

    fun setMessagesList(messages: List<Message>) {
        messagesList.clear()
        messagesList.addAll(messages)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = SingleMessageRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(messagesList[position])
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }
}

class MyViewHolder(private val binding: SingleMessageRecyclerViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(message: Message) {
        binding.apply {

        }
    }

}