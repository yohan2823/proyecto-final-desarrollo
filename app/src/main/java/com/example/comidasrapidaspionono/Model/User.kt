package com.example.fastfoodmanagement.Model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "client",
    val profileImageUrl: String = "",
    val phoneNumber: String = ""
)