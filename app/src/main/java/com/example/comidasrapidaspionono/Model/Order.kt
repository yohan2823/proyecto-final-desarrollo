package com.example.fastfoodmanagement.Model

import java.util.Date

data class Order(
    val id: String = "",
    val userId: String = "",
    val productList: List<OrderItem> = emptyList(),
    val totalAmount: Double = 0.0,
    val status: String = "pending",
    val orderDate: Date = Date()
)