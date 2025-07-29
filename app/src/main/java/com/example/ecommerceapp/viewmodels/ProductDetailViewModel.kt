package com.example.ecommerceapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val firestoreRepository: FirestoreRepository) :
    ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> get() = _product

    fun fetchProductDetails(productId: String) {
        viewModelScope.launch {
            try {
                val product = firestoreRepository.getProductById(productId)
                _product.value = product
            } catch (e: Exception) {
                Log.e("ProductDetailViewModel", "Product not found: ${e.message}")

            }
        }
    }
}