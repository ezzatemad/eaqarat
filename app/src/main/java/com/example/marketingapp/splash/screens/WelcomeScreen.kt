package com.example.marketingapp.splash.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.marketingapp.Dimension
import com.example.marketingapp.R

@Composable
fun WelcomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome_iv),
                contentDescription = "Welcome Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.5f)
            )
            Text(
                text = "Welcome",
                fontWeight = FontWeight.Bold,
                fontSize = Dimension.LargeFontSize,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimension.LargePadding, end = Dimension.LargePadding, top = Dimension.LargePadding),
                color = colorResource(id = R.color.green)
            )
            Text(
                text = "eaqarat misr app allows users to browse properties for sale or rent",
                fontWeight = FontWeight.SemiBold,
                fontSize = Dimension.MediumFontSize,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimension.LargePadding, end = Dimension.LargePadding, top = Dimension.LargePadding),
                color = colorResource(id = R.color.green)
            )
        }
        Button(
            onClick = { /* Handle button click */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(Dimension.LargePadding)
        ) {
            Text(text = "Get Started")
        }
    }
}