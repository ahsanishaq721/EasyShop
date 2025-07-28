package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.screens.profile.ProfileScreen
import com.example.ecommerceapp.screens.cart.CartScreen
import com.example.ecommerceapp.screens.categories.CategoryScreen
import com.example.ecommerceapp.screens.home.HomeScreen
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.screens.products.ProductDetailScreen
import com.example.ecommerceapp.screens.products.ProductScreen
import com.example.ecommerceapp.ui.theme.EcommerceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcommerceAppTheme {
                val navController = rememberNavController()
                // Nav Host
                NavHost(
                    navController = navController,
                    startDestination = "Home"
                ) {
                    // define routes using composable
                    // for each screen you want to support
                    composable(Screens.Home.route) {
                        HomeScreen(
                            navController = navController,
                            onProfileClick = { navController.navigate(Screens.Profile.route) },
                            onCartClick = { navController.navigate(Screens.Cart.route) }
                        )
                    }
                    composable(Screens.Cart.route) {
                        CartScreen(navCont = navController)
                    }

                    composable(Screens.Profile.route) {
                        ProfileScreen(navCont = navController, onSignOut = {})
                    }
                    composable(Screens.Categories.route) {
                        CategoryScreen(navCont = navController, onCategoryClick = {})
                    }
                    composable(Screens.ProductsDetails.route) {
                        val productId = it.arguments?.getString("productId")
                        if (productId != null)
                            ProductDetailScreen(productId = productId)

                    }
                    composable(Screens.ProductList.route) {
                        val categoryId = it.arguments?.getString("categoryId")
                        if (categoryId != null)
                            ProductScreen(categoryId = categoryId, navController)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EcommerceAppTheme {
        HomeScreen(navController = rememberNavController(), {}, {})
    }
}