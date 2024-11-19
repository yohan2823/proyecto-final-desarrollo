package com.example.fastfoodmanagement.Model

data class AppConfig(
    val id: String = "default",
    val taxRate: Double = 0.0,
    val currency: String = "USD",
    val isMenuAvailable: Boolean = true
)