package com.example.ecommerceapp.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ecommerceapp.model.Category
import com.example.ecommerceapp.model.Product

@Composable
fun HomeScreen(navController: NavHostController, onProfileClick: () -> Unit, onCartClick: () -> Unit) {
    Scaffold(
        topBar = { MyTopAppBar(onProfileClick, onCartClick) },
        bottomBar = { BottomNavigationBar() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // Search section
            var searchQuery by remember { mutableStateOf("") }
            val focusManager = LocalFocusManager.current
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }, // Update the state here
                onSearch = { focusManager.clearFocus() },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )

            // categories section

            SectionTitle(title = "Categories", actionText = "View All") {
                navController.navigate("Categories")
            }

            Spacer(Modifier.height(16.dp))

            // mock the categories
            val categories = listOf(
                Category(
                    1,
                    "Electronics",
                    "https://cdn-icons-png.flaticon.com/512/1555/1555401.png"
                ),
                Category(
                    2,
                    "Clothing",
                    "https://cdn-icons-png.flaticon.com/512/2935/2935183.png"
                ),
            )

            var selectedCategory by remember { mutableStateOf(0) }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            )
            {
                // Using items(items, key) is often better for performance and stability
                items(items = categories) { category ->
                    CategoryChip(
                        icon = category.imageUrl,
                        text = category.name,
                        isSelected = selectedCategory == category.id,
                        onClick = { selectedCategory = category.id }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // features product section
            SectionTitle(title = "Featured", actionText = "View All") {
                /* Handle action click */
            }

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
                    imageUrl = "https://image.pngaaa.com/404/1144404-middle.png",                ),
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(products) { product ->
                    FeaturedProductCard(product) { }
                }
            }

        }
    }
}