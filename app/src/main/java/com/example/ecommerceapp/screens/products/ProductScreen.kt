package com.example.ecommerceapp.screens.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.screens.navigation.Screens

@Composable
fun ProductScreen(
    categoryId: String,
    navController: NavHostController
) {
    val products = listOf(
        Product(
            id = "1",
            name = "Smart phone",
            price = 999.99,
            imageUrl = "https://image.pngaaa.com/404/1144404-middle.png",
        ),
        Product(
            id = "2",
            name = "Laptop",
            price = 1999.99,
            imageUrl = "https://image.pngaaa.com/404/1144404-middle.png",
        ),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Category ID: $categoryId",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        if (products.isEmpty()) {
            Text(
                text = "No products found",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
                items(products) {
                    ProductItem(
                        product = it,
                        onClick = { navController.navigate(Screens.ProductsDetails.createRoute(it.id)) },
                        onAddToCart = {}
                    )
                }
            }
        }
    }
}