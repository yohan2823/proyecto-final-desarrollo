package com.example.fastfoodmanagement.Model

data class Review(
    val id: String = "",
    val userId: String = "",
    val productId: String = "",
    val rating: Int = 0,
    val comment: String = "",
    val reviewDate: Long = System.currentTimeMillis()
)