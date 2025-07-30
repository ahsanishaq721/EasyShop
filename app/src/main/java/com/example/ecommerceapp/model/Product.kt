package com.example.ecommerceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val autoId: Int = 0,
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val categoryId: String = ""
)
