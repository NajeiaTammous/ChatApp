package com.example.chatapp

import android.os.Parcelable


data class UsersModel(
    val id : String = "",
    val username : String ="",
    val email : String ="",
    val password : String ="",
    val isOnline : Boolean = false
) {
}