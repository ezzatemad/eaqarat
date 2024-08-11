@file:OptIn(ExperimentalFoundationApi::class)

package com.example.marketingapp.splash.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.marketingapp.Dimension
import com.example.marketingapp.R
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.second_iv),
                contentDescription = "Second Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.6f),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Explore real estate",
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
                text = "Discover a wide range of properties for sale and rent",
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
                coroutineScope.launch {
                    pagerState.animateScrollToPage(page = 2)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(Dimension.LargePadding),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.green),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Get Started",
                color = Color.White
            )
        }
    }
}