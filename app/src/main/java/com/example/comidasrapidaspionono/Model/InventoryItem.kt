package com.example.fastfoodmanagement.Model

data class InventoryItem(
    val id: String = "",
    val productId: String = "",
    val quantity: Int = 0,
    val lastUpdated: Long = System.currentTimeMillis()
)