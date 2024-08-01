package com.example.marketingapp.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.marketingapp.home.MainActivity
import com.example.marketingapp.TokenManager
import com.example.marketingapp.register.RegisterActivity
import com.example.marketingapp.ui.theme.splasch_background
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen()
        }
    }
}

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val tokenManager = TokenManager(context = context)
    LaunchedEffect(Unit) {
        delay(2000) // 2 seconds delay

        if (tokenManager.isTokenAvailable()) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            (context as? Activity)?.finish()
        } else {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
            (context as? Activity)?.finish()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(splasch_background)
    )
}
