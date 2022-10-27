package com.example.contactsapp.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.model.get_message.Message
import com.example.contactsapp.repository.MessagesRepository
import com.example.contactsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostMessageViewModel @Inject constructor(private val messagesRepository: MessagesRepository) :
    ViewModel() {
    private val _postMessageStateFlow: MutableStateFlow<Resource<Message>> =
        MutableStateFlow(Resource.Initial())

    val postMessageStateFlow: StateFlow<Resource<Message>> = _postMessageStateFlow

    fun postMessage(from: String, to: String, body: String) = viewModelScope.launch {
        _postMessageStateFlow.value = Resource.Loading()

        messagesRepository.postMessage(from, to, body)
            .catch { e ->
                _postMessageStateFlow.value =
                    Resource.Error(message = e.message ?: "An Unknown Error Occurred")
            }.collect {
                _postMessageStateFlow.value = Resource.Success(data = it)
            }
    }
}