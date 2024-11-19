package com.example.fastfoodmanagement.services

import com.example.fastfoodmanagement.Model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserService {
    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("users")

    suspend fun addUser(user: User): Boolean {
        return try {
            userCollection.add(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteUser(userId: String): Boolean {
        return try {
            userCollection.document(userId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getUsers(): List<User> {
        return try {
            userCollection.get().await().toObjects(User::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}