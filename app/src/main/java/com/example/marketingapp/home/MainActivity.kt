package com.example.marketingapp.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.marketingapp.TokenManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: HomeViewModel = hiltViewModel()
            val context = LocalContext.current
            val getAllPropertyResponse by viewModel.getAllPropertyResponse.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()

            val tokenManager = TokenManager(context)
            val token = tokenManager.getToken()

            Log.d("MainActivity", "Token: $token")

            LaunchedEffect(Unit) {
                token?.let {
                    Log.d("MainActivity", "Calling getAllProperty with token: $it")
                    viewModel.getAllProperty(it)
                }
            }

            LaunchedEffect(getAllPropertyResponse) {
                getAllPropertyResponse?.let {
                    Log.d("MainActivity", "Property Response in LaunchedEffect: $it")
                }
            }

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                getAllPropertyResponse?.let {
                    Log.d("MainActivity", "Property Response in UI: $it")
                }
            }
        }
    }
    @Composable
    fun GlideImage(
        imageUrl: String,
        modifier: Modifier = Modifier,
        contentDescription: String? = null
    ) {
        val context = LocalContext.current
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

        LaunchedEffect(imageUrl) {
            Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imageBitmap = resource.asImageBitmap()
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Handle cleanup if needed
                    }
                })
        }

        imageBitmap?.let {
            Image(
                bitmap = it,
                contentDescription = contentDescription,
                modifier = modifier
            )
        }
    }
}