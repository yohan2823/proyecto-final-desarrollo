package com.example.fastfoodmanagement.Model

data class OrderItem(
    val productId: String = "",
    val quantity: Int = 1,
    val price: Double = 0.0
)