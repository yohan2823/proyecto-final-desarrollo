package com.example.fastfoodmanagement.Model

data class Employee(
    val id: String = "",
    val name: String = "",
    val role: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val hireDate: Long = System.currentTimeMillis()
)