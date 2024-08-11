@file:OptIn(ExperimentalFoundationApi::class)

package com.example.marketingapp.splash.screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.marketingapp.Dimension
import com.example.marketingapp.R
import com.example.marketingapp.register.RegisterActivity

class SecondSplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecondSplashScreen()
        }
    }
}

@Composable
fun SecondSplashScreen() {

    val context = LocalContext.current

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray2))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    0 -> WelcomeScreen(pagerState)
                    1 -> OnboardingScreen(pagerState)
                    2 -> GetStartedScreen()
                }
            }
            PagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .padding(bottom = Dimension.biggestLargePadding)
            )
        }
    }
}

@Composable
fun PagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = colorResource(id = R.color.green),
    inactiveColor: Color = Color.White
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pagerState.pageCount) { page ->
            val color = if (pagerState.currentPage == page) activeColor else inactiveColor
            Box(
                modifier = Modifier
                    .size(height = Dimension.MediumPadding, width = Dimension.LargePadding)
                    .padding(horizontal = 4.dp)
                    .background(color, shape = CircleShape)
                    .clip(RoundedCornerShape(Dimension.SmallCornerRadius)),
            )
        }
    }
}
