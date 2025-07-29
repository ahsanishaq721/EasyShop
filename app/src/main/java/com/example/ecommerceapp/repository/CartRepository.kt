package com.example.ecommerceapp.repository

import android.util.Log
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.room.CartDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(private val cartDao: CartDao) {

    val allCartItems: Flow<List<Product>> = cartDao.getAllCartItems()
    suspend fun addToCart(product: Product) {
        val existingItem = cartDao.getCartItemById(product.id)
        if (existingItem != null) {
            Log.d("CartRepository", "Item already exists in the cart")
            cartDao.updateCartItem(product)
        } else {
            cartDao.insertCartItem(product)
        }
    }

    suspend fun deleteCartItem(cartItem: Product) {
        cartDao.deleteCartItem(cartItem)
    }
    suspend fun clearCart() {
        cartDao.clearCart()
    }


}