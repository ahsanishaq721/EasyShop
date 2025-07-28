package com.example.ecommerceapp.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ecommerceapp.model.Category
import com.example.ecommerceapp.screens.navigation.Screens

@Composable
fun CategoryScreen(navCont: NavHostController, onCategoryClick: () -> Unit) {
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
    if (categories.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No categories found", style = MaterialTheme.typography.titleLarge)
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            items(categories) {
                CategoryItem(
                    category = it,
                    onClick = { navCont.navigate(Screens.ProductList.createRoute(it.id)) })
            }
        }
    }
}