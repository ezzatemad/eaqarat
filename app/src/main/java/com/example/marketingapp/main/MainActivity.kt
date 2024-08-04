package com.example.marketingapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.getValue
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.domain.datamodel.getallproperty.DataItem
import com.example.marketingapp.R
import com.example.marketingapp.account.AccountScreen
import com.example.marketingapp.home.HomeScreen
import com.example.marketingapp.saved.SavedScreen
import com.example.marketingapp.search.SearchScreen


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
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = "Search"
                            )
                        },
                        label = { Text("Search") },
                        selected = currentRoute == "search",
                        onClick = {
                            if (currentRoute != "search") {
                                navController.navigate("search") {
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
                                painter = painterResource(id = R.drawable.ic_seitting),
                                contentDescription = "Setting"
                            )
                        },
                        label = { Text("Setting") },
                        selected = currentRoute == "setting",
                        onClick = {
                            if (currentRoute != "setting") {
                                navController.navigate("setting") {
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
                composable("home") { HomeScreen() }
                composable("search") { SearchScreen(navController = navController) }
                composable("saved") { SavedScreen() }
                composable("account") { AccountScreen() }
            }
        }
    }
}
