package com.example.contactsapp.network

import com.example.contactsapp.model.get_message.GetMessageModel
import com.example.contactsapp.utils.Constants
import retrofit2.http.GET

interface ApiService {
    @GET(Constants.ACCOUNT_SID + "/" + Constants.END_POINT)
    suspend fun getAllMessages(): GetMessageModel
}