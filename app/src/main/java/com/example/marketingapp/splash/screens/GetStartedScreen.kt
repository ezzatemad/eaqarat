@file:OptIn(ExperimentalFoundationApi::class)

package com.example.marketingapp.splash.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.animation.content.Content
import com.example.marketingapp.Dimension
import com.example.marketingapp.R
import com.example.marketingapp.register.RegisterActivity
import kotlinx.coroutines.launch


@Composable
fun GetStartedScreen() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.thrid_iv),
                contentDescription = "Third Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.6f),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Find your ideal property",
                fontWeight = FontWeight.Bold,
                fontSize = Dimension.LargeFontSize,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Dimension.LargePadding,
                        end = Dimension.LargePadding,
                        top = Dimension.LargePadding
                    ),
                color = colorResource(id = R.color.green)
            )
            Text(
                text = "Find your perfect property using advanced filters",
                fontWeight = FontWeight.SemiBold,
                fontSize = Dimension.MediumFontSize,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Dimension.LargePadding,
                        end = Dimension.LargePadding,
                        top = Dimension.LargePadding
                    ),
                color = colorResource(id = R.color.green)
            )
        }
        Button(
            onClick = {
                val intent = Intent(context, RegisterActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(Dimension.LargePadding),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.green),
                contentColor = colorResource(id = R.color.white)
            )
        ) {
            Text(text = "Get Started")
        }
    }
}