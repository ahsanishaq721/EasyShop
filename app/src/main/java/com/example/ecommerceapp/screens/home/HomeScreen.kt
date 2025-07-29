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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.viewmodels.CategoryViewModel
import com.example.ecommerceapp.viewmodels.ProductViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    onProfileClick: () -> Unit,
    onCartClick: () -> Unit,
    productViewModel: ProductViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
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
                navController.navigate(Screens.Categories.route)
            }

            Spacer(Modifier.height(16.dp))

            val categories by categoryViewModel.categories.collectAsStateWithLifecycle()

            var selectedCategory by remember { mutableStateOf(0) }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            )
            {
                // Using items(items, key) is often better for performance and stability
                items(items = categories) { category ->
                    CategoryChip(
                        icon = category.iconUrl,
                        text = category.name,
                        isSelected = selectedCategory == category.id,
                        onClick = { selectedCategory = category.id
                        navController.navigate(Screens.ProductList.createRoute(category.id))
                        }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // features product section
            SectionTitle(title = "Featured", actionText = "View All") {
                /* Handle action click */
            }

            productViewModel.getAllProductsFromFireStore()
            val products by productViewModel.allProducts.collectAsStateWithLifecycle()

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(products) { product ->
                    FeaturedProductCard(product) {
                        navController.navigate(Screens.ProductsDetails.createRoute(product.id))
                    }
                }
            }

        }
    }
}