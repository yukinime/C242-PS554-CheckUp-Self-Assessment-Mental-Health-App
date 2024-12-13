package com.aruel.checkupapp.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false,
    val username: String = ""
)