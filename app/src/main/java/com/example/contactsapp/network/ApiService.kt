package com.example.contactsapp.network

import com.example.contactsapp.model.get_message.GetMessageModel
import com.example.contactsapp.model.get_message.Message
import com.example.contactsapp.utils.Constants
import retrofit2.http.*

interface ApiService {
    @GET(Constants.ACCOUNT_SID + "/" + Constants.END_POINT)
    suspend fun getAllMessages(): GetMessageModel

    @FormUrlEncoded
    @POST(Constants.ACCOUNT_SID + "/" + Constants.END_POINT)
    suspend fun postMessage(@Field("From") from: String, @Field("To") to: String, @Field("Body") body: String): Message
}