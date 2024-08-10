package com.example.marketingapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.getValue
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.*
import com.example.marketingapp.R
import com.example.marketingapp.account.AccountScreen
import com.example.marketingapp.favorite.FavoriteScreen
import com.example.marketingapp.search.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }


    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        val currentDestination by navController.currentBackStackEntryAsState()
        val currentRoute = currentDestination?.destination?.route ?: "home"

        Scaffold(
            bottomBar = {
                BottomNavigation(
                    backgroundColor = Color.White
                ) {
//                    BottomNavigationItem(
//                        icon = {
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_home),
//                                contentDescription = "Home"
//                            )
//                        },
//                        label = { Text("Home") },
//                        selected = currentRoute == "home",
//                        onClick = {
//                            if (currentRoute != "home") {
//                                navController.navigate("home") {
//                                    // Clear back stack to prevent navigating back to the same screen
//                                    popUpTo(navController.graph.startDestinationId) {
//                                        saveState = true
//                                    }
//                                    launchSingleTop = true
//                                    restoreState = true
//                                }
//                            }
//                        },
//                        selectedContentColor = colorResource(id = R.color.green),
//                        unselectedContentColor = colorResource(id = R.color.gray)
//                    )
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_home),
                                contentDescription = "Home"
                            )
                        },
                        label = { Text("Home") },
                        selected = currentRoute == "home",
                        onClick = {
                            if (currentRoute != "home") {
                                navController.navigate("home") {
                                    // Clear back stack to prevent navigating back to the same screen
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        selectedContentColor = colorResource(id = R.color.green),
                        unselectedContentColor = colorResource(id = R.color.gray)
                    )
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_favorite),
                                contentDescription = "favorite"
                            )
                        },
                        label = { Text("Favorite") },
                        selected = currentRoute == "favorite",
                        onClick = {
                            if (currentRoute != "favorite") {
                                navController.navigate("favorite") {
                                    // Clear back stack to prevent navigating back to the same screen
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        selectedContentColor = colorResource(id = R.color.green),
                        unselectedContentColor = colorResource(id = R.color.gray)
                    )
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_account),
                                contentDescription = "Account"
                            )
                        },
                        label = { Text("Account") },
                        selected = currentRoute == "account",
                        onClick = {
                            if (currentRoute != "account") {
                                navController.navigate("account") {
                                    // Clear back stack to prevent navigating back to the same screen
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        selectedContentColor = colorResource(id = R.color.green),
                        unselectedContentColor = colorResource(id = R.color.gray)
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                Modifier.padding(innerPadding)
            ) {
                composable("home") { HomeScreen(navController = navController) }
                composable("favorite") { FavoriteScreen() }
                composable("account") { AccountScreen() }
            }
        }
    }
}
