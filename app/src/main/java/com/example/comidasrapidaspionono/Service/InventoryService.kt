package com.example.fastfoodmanagement.services


import com.example.fastfoodmanagement.Model.InventoryItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class InventoryService {
    private val db = FirebaseFirestore.getInstance()
    private val inventoryCollection = db.collection("inventory")

    suspend fun addInventoryItem(item: InventoryItem): Boolean {
        return try {
            inventoryCollection.add(item).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteInventoryItem(itemId: String): Boolean {
        return try {
            inventoryCollection.document(itemId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getInventoryItems(): List<InventoryItem> {
        return try {
            inventoryCollection.get().await().toObjects(InventoryItem::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}