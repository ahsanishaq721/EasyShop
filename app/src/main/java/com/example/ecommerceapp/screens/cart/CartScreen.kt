package com.example.ecommerceapp.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.ui.theme.EcommerceAppTheme

@Composable
fun CartScreen(navCont: NavHostController) {
    val cartItems = listOf(
        Product(
            id = "1",
            name = "Smart phone",
            price = 999.99,
            imageUrl = "https://image.pngaaa.com/404/1144404-middle.png",
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Your Cart", style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (cartItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Your cart is empty", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(16.dp))
                Button(onClick = {}) {
                    Text(text = "Continue Shopping")
                }
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) {
                    CartItemCard(it) {
                    }
                }
            }
        }

        // total and checkout section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(text = "Total", style = MaterialTheme.typography.titleMedium)

                Text(
                    text = "$${cartItems.sumOf { it.price }}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
                Text(text = "Proceed to Checkout")
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    EcommerceAppTheme {
        CartScreen(navCont = NavHostController(context = LocalContext.current))
    }
}