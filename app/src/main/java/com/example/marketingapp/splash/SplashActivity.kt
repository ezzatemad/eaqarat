package com.example.marketingapp.splash

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import com.example.marketingapp.Dimension
import com.example.marketingapp.R
import kotlinx.coroutines.delay
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.marketingapp.main.MainActivity
import com.example.marketingapp.TokenManager
import com.example.marketingapp.splash.screens.SecondSplashActivity
import dagger.hilt.android.AndroidEntryPoint

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

    val tokenManager = TokenManager(context)

    LaunchedEffect(Unit) {
        delay(2000)
        if (tokenManager.isTokenAvailable()) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
        } else {
            val intent = Intent(context, SecondSplashActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.green)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Eaqarat Misr",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimension.LargePadding, end = Dimension.LargePadding),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = Dimension.BiggestLargerFontSize
            )
        }
    }
}
