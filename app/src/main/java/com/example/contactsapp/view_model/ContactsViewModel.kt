package com.example.contactsapp.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.model.SingleContact
import com.example.contactsapp.utils.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type

class ContactsViewModel : ViewModel() {

    private val _contacts = MutableStateFlow<Resource<List<SingleContact>>>(Resource.Initial())
    val contacts: StateFlow<Resource<List<SingleContact>>> = _contacts

    fun fetchContacts(context: Context) {

        try {
            val contactsList: List<SingleContact> = loadJSONFromAsset(context)

            if (contactsList.isEmpty()) {
                viewModelScope.launch {
                    _contacts.emit(Resource.Error(message = "Contacts List is Empty"))
                }
            } else {
                viewModelScope.launch {
                    _contacts.emit(Resource.Success(contactsList))
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            viewModelScope.launch {
                _contacts.emit(Resource.Error(message = e.message ?: "An Unknown Error Occurred"))
            }
        }

    }

    private fun loadJSONFromAsset(context: Context): List<SingleContact> {
        val json: String? = try {
            val inputStream: InputStream = context.assets.open("Contacts.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (exception: IOException) {
            exception.printStackTrace()
            return emptyList()
        }

        val type: Type = object : TypeToken<List<SingleContact>>() {}.type

        return Gson().fromJson(json, type)
    }
}