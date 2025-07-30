package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.screens.profile.ProfileScreen
import com.example.ecommerceapp.screens.cart.CartScreen
import com.example.ecommerceapp.screens.categories.CategoryScreen
import com.example.ecommerceapp.screens.home.BottomNavigationBar
import com.example.ecommerceapp.screens.home.HomeScreen
import com.example.ecommerceapp.screens.home.MyTopAppBar
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.screens.products.ProductDetailScreen
import com.example.ecommerceapp.screens.products.ProductScreen
import com.example.ecommerceapp.screens.profile.SignInScreen
import com.example.ecommerceapp.screens.profile.SignUpScreen
import com.example.ecommerceapp.ui.theme.EcommerceAppTheme
import com.example.ecommerceapp.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcommerceAppTheme {
                val authViewModel = hiltViewModel<AuthViewModel>()
                val isLoggedIn by remember {
                    derivedStateOf {
                        authViewModel.isLoggedIn
                    }
                }

                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { paddingValues ->
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
                            CartScreen(navCont = navController, Modifier.padding(paddingValues))
                        }

                        composable(Screens.Profile.route) {
                            ProfileScreen(onSignOut = {
                                authViewModel.signOut()
                                navController.navigate(Screens.SignIn.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            })
                        }
                        composable(Screens.Categories.route) {
                            CategoryScreen(
                                navCont = navController,
                                Modifier.padding(paddingValues),
                                onCartClick = { navController.navigate(Screens.Cart.route) },
                                onProfileClick = {
                                    if (!isLoggedIn)
                                        navController.navigate(Screens.SignIn.route)
                                    else
                                        navController.navigate(Screens.Profile.route)
                                }
                            )

                        }
                        composable(Screens.ProductsDetails.route) {
                            val productId = it.arguments?.getString("productId")
                            if (productId != null)
                                ProductDetailScreen(
                                    productId = productId,
                                    Modifier.padding(paddingValues)
                                )
                        }
                        composable(Screens.ProductList.route) {
                            val categoryId = it.arguments?.getString("categoryId")
                            if (categoryId != null)
                                ProductScreen(
                                    Modifier.padding(paddingValues),
                                    categoryId = categoryId,
                                    navController
                                )
                        }
                        composable(Screens.SignIn.route) {
                            SignInScreen(
                                onSignInSuccess = {
                                    // Navigate to Home and pop all authentication screens from the back stack
                                    navController.navigate(Screens.Home.route) {
                                        popUpTo(Screens.SignIn.route) { // Pop up to the SignIn route
                                            inclusive = true
                                        }
                                    }
                                },
                                onNavigateToSignUp = {
                                    navController.navigate(Screens.SignUp.route) {
                                    }
                                }
                            )
                        }
                        composable(Screens.SignUp.route) {
                            SignUpScreen(
                                onSignUpSuccess = {
                                    // Navigate to Home and pop all authentication screens from the back stack
                                    navController.navigate(Screens.Home.route) {
                                        popUpTo(Screens.SignUp.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                onNavigateToLogin = {
                                    // Navigate back to SignIn and remove SignUp from the back stack
                                    navController.navigate(Screens.SignIn.route) {
                                        popUpTo(Screens.SignUp.route) {
                                            inclusive =
                                                true // Remove SignUp screen when going back to SignIn
                                        }
                                    }
                                }
                            )
                        }
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