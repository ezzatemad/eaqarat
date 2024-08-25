package com.example.marketingapp.account

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.marketingapp.Dimension
import com.example.marketingapp.R

@Composable
fun AccountScreen(navController: NavController) {
    ProfileDetails(navController = navController)
}

@Composable
fun ProfileDetails(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = Dimension.LargePadding,
                    top = Dimension.MediumPadding,
                    bottom = Dimension.MediumPadding
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.navigate("home") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    tint = colorResource(id = R.color.green)
                )
            }
        }

        Text(
            text = "Profile",
            fontSize = Dimension.LargeFontSize,
            color = colorResource(id = R.color.green),
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimension.MediumPadding),
        )

        Image(
            painter = painterResource(id = R.drawable.profile_iv),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(Dimension.MediumSpace))

        Text(
            text = "Ezzat Emad",
            color = colorResource(id = R.color.green),
            fontSize = Dimension.LargeFontSize,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimension.LargePadding),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Dimension.LargeSpace))

        ProfileInfoCard(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun ProfileInfoCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = Dimension.BiggestLargeCornerRadius,
                    topEnd = Dimension.BiggestLargeCornerRadius,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.green))
                .padding(Dimension.MediumPadding)
        ) {
            ProfileTextField(
                text = "Ezzat",
                onTextChange = { "Ezzat Emad" },
                modifier = Modifier,
                label = "Name",
                maxLines = 1
            )
            ProfileTextField(
                text = "Ezzat",
                onTextChange = { "Ezzat Emad" },
                modifier = Modifier,
                label = "Name",
                maxLines = 1
            )
            ProfileTextField(
                text = "Ezzat",
                onTextChange = { "Ezzat Emad" },
                modifier = Modifier,
                label = "Name",
                maxLines = 1
            )
            ProfileTextField(
                text = "Ezzat",
                onTextChange = { "Ezzat Emad" },
                modifier = Modifier,
                label = "Name",
                maxLines = 1
            )
            ProfileTextField(
                text = "Ezzat",
                onTextChange = { "Ezzat Emad" },
                modifier = Modifier,
                label = "Name",
                maxLines = 1
            )
            ProfileTextField(
                text = "Ezzat",
                onTextChange = { "Ezzat Emad" },
                modifier = Modifier,
                label = "Name",
                maxLines = 1
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier,
    label: String,
    maxLines: Int,
    icon: Int? = null,
    error: String? = null
) {
    Column {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text(text = label) },
            maxLines = maxLines,
            shape = RoundedCornerShape(Dimension.SmallCornerRadius),
            textStyle = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.ExtraLight,
                fontSize = Dimension.MediumFontSize
            ),
            leadingIcon = icon?.let {
                {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        tint = colorResource(id = R.color.white)
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.white),
                unfocusedBorderColor = colorResource(id = R.color.white),
                containerColor = Color.Transparent,
                unfocusedLabelColor = colorResource(id = R.color.white),
                focusedLabelColor = Color.White,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = colorResource(id = R.color.white)
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = Dimension.LargePadding,
                    end = Dimension.LargePadding
                )
        )
        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                style = TextStyle(fontSize = Dimension.SmallFontSize),
                modifier = Modifier.padding(start = Dimension.LargePadding)
            )
        }
    }
}

