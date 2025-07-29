package com.example.ecommerceapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerceapp.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: Product)

    @Insert
    suspend fun updateCartItem(cartItem: Product)

    @Insert
    suspend fun deleteCartItem(cartItem: Product)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<Product>>

    @Query("SELECT * FROM cart_items WHERE id = :productId")
    suspend fun getCartItemById(productId: String)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

}