package com.example.contactsapp.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.model.get_message.GetMessageModel
import com.example.contactsapp.repository.GetMessagesRepository
import com.example.contactsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetMessagesViewModel @Inject constructor(private val getMessagesRepository: GetMessagesRepository) :
    ViewModel() {
    private val _getMessageStateFlow: MutableStateFlow<Resource<GetMessageModel>> =
        MutableStateFlow(Resource.Initial())

    val getMessageStateFlow: StateFlow<Resource<GetMessageModel>> = _getMessageStateFlow

    fun getAllMessages() = viewModelScope.launch {
        _getMessageStateFlow.value = Resource.Loading()

        getMessagesRepository.getAllMessages()
            .catch { e ->
                _getMessageStateFlow.value =
                    Resource.Error(message = e.message ?: "An Unknown Error Occurred")
            }.collect {
                _getMessageStateFlow.value = Resource.Success(data = it)
            }
    }
}