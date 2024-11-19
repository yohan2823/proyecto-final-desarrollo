package com.example.fastfoodmanagement.services

import com.example.fastfoodmanagement.Model.Review
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ReviewService {
    private val db = FirebaseFirestore.getInstance()
    private val reviewCollection = db.collection("reviews")

    suspend fun addReview(review: Review): Boolean {
        return try {
            reviewCollection.add(review).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteReview(reviewId: String): Boolean {
        return try {
            reviewCollection.document(reviewId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getReviews(): List<Review> {
        return try {
            reviewCollection.get().await().toObjects(Review::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}