package com.example.marketingapp.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.marketingapp.TokenManager
import kotlin.math.log

@Composable
fun HomeScreen() {

    val context = LocalContext.current
    val tokenManager = TokenManager(context)
    Log.d("TAG", "HomeScreen: ${tokenManager.getToken()}")
}


