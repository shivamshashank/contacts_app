package com.example.contactsapp.network

import com.example.contactsapp.model.get_message.GetMessageModel
import com.example.contactsapp.model.get_message.Message
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllMessages(): GetMessageModel = apiService.getAllMessages()

    suspend fun postMessage(from: String, to: String, body: String): Message =
        apiService.postMessage(from, to, body)
}