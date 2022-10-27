package com.example.contactsapp.network

import com.example.contactsapp.model.get_message.GetMessageModel
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllMessages(): GetMessageModel = apiService.getAllMessages()

}