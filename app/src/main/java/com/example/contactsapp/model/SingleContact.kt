package com.example.contactsapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SingleContact(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone: String,
) : Parcelable