package com.example.contactsapp.model.get_message

data class Message(
    val body: String,
    val date_created: String,
    val error_code: Any,
    val error_message: Any,
    val from: String,
    val status: String,
    val to: String,
)