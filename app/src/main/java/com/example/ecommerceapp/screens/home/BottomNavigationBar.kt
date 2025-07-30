package com.example.ecommerceapp.screens.home

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.screens.navigation.Screens
import com.example.ecommerceapp.ui.theme.EcommerceAppTheme
import com.example.ecommerceapp.viewmodels.CartViewModel

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel()
) {

    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle(emptyList())

    val items = listOf(
        BottomNavItem(
            title = "Home", icon = Icons.Default.Home, route = Screens.Home.route
        ), BottomNavItem(
            title = "Categories", icon = Icons.Default.Search, route = Screens.Categories.route
        ), BottomNavItem(
            title = "Wishlist",
            icon = Icons.Default.Favorite,
            route = Screens.Cart.route,
            badgeCount = 5
        ), BottomNavItem(
            title = "Cart",
            icon = Icons.Default.ShoppingCart,
            route = Screens.Cart.route,
            badgeCount = cartViewModel.totalItems(cartItems)
        ), BottomNavItem(
            title = "Profile", icon = Icons.Default.Person, route = Screens.Profile.route
        )
    )
    NavigationBar(
        containerColor = Color.White, tonalElevation = 8.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach {
            NavigationBarItem(
                selected = currentRoute == it.route,
                onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = true,
                icon = {
                    if (it.badgeCount > 0) {
                        BadgedBox(badge = {
                            Badge {
                                Text(text = it.badgeCount.toString())
                            }
                        }) {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.title,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.title,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                },
                label = {
                    Text(
                        text = it.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
            )
        }
    }

}

data class BottomNavItem(
    val title: String, val icon: ImageVector, val route: String, val badgeCount: Int = 0
)

@Preview(showBackground = true)
@Composable
fun BottomNavPreview() {
    EcommerceAppTheme {
        BottomNavigationBar(navController = rememberNavController())
    }
}