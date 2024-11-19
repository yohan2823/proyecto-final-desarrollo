package com.example.fastfoodmanagement.services

import com.example.fastfoodmanagement.Model.Order
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class OrderService {
    private val db = FirebaseFirestore.getInstance()
    private val orderCollection = db.collection("orders")

    suspend fun placeOrder(order: Order): Boolean {
        return try {
            orderCollection.add(order).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteOrder(orderId: String): Boolean {
        return try {
            orderCollection.document(orderId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getOrders(): List<Order> {
        return try {
            orderCollection.get().await().toObjects(Order::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}