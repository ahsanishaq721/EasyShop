package com.example.ecommerceapp.screens.navigation

sealed class Screens(val route: String) {
    object Home : Screens("Home")
    object Categories : Screens("Categories")
    object Profile : Screens("Profile")
    object Cart : Screens("Cart")
    object CategoryList : Screens("category_list")
    object ProductsDetails : Screens("product_details/{productId}") {
        fun createRoute(productId: String) = "product_details/$productId"
    }

    object ProductList : Screens("product_list/{categoryId}") {
        fun createRoute(categoryId: Int) = "product_list/$categoryId"
    }


}