package com.example.ecommerceapp.repository

import com.example.ecommerceapp.model.Category
import com.example.ecommerceapp.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getCategoriesFlow(): Flow<List<Category>> =
        callbackFlow {
            val listenerRegistration = firestore.collection("categories")
                .addSnapshotListener { snapshot, error ->

                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val categories = snapshot.documents.mapNotNull { doc ->
                            doc.toObject(Category::class.java)
                        }
                        trySend(categories)
                    }
                }
            awaitClose { listenerRegistration.remove() }
        }

    suspend fun getProductsByCategoryId(categoryId: String): List<Product> {
        return try {
            val querySnapshot = firestore.collection("products")
                .whereEqualTo("categoryId", categoryId)
                .get()
                .await()
            querySnapshot.documents.mapNotNull { doc ->
                doc.toObject(Product::class.java)
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    suspend fun getProductById(productId: String): Product? {
        return try {
            val documentSnapshot = firestore.collection("products")
                .document(productId)
                .get()
                .await()
            documentSnapshot.toObject(Product::class.java)
        } catch (_: Exception) {
            null
        }
    }

    suspend fun getAllProductsInFireStore(): List<Product> {
        return try {
            val allProducts = firestore.collection("products")
                .get()
                .await()
                .documents.mapNotNull { it.toObject(Product::class.java) }
            allProducts
        } catch (_: Exception) {
            emptyList()
        }
    }
}