package com.example.contactsapp.repository

import com.example.contactsapp.model.get_message.GetMessageModel
import com.example.contactsapp.model.get_message.Message
import com.example.contactsapp.network.ApiServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MessagesRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {

    fun getAllMessages(): Flow<GetMessageModel> = flow {
        emit(apiServiceImpl.getAllMessages())
    }.flowOn(Dispatchers.IO)

    fun postMessage(from: String, to: String, body: String): Flow<Message> = flow {
        emit(apiServiceImpl.postMessage(from, to, body))
    }.flowOn(Dispatchers.IO)
}