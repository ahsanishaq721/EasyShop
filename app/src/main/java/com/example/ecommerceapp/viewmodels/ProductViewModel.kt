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
class ProductViewModel @Inject constructor(private val firestoreRepository: FirestoreRepository) :
    ViewModel() {

    // Encapsulation: Mutable internally but read only externally
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    fun fetchProductsByCategoryId(categoryId: String) {
        viewModelScope.launch {
            try {
                val products = firestoreRepository.getProductsByCategoryId(categoryId)
                _products.value = products
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error fetching products: ${e.message}")
            }
        }
    }

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> get() = _allProducts

    fun getAllProductsFromFireStore() {
        viewModelScope.launch {
            try {
                val products = firestoreRepository.getAllProductsInFireStore()
                _allProducts.value = products
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error fetching products: ${e.message}")
            }
        }
    }

}