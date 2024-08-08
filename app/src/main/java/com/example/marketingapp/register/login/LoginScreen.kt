package com.example.marketingapp.register.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.datamodel.register.login.LoginRequest
import com.example.marketingapp.Dimension
import com.example.marketingapp.main.MainActivity
import com.example.marketingapp.R
import com.example.marketingapp.TokenManager
import com.example.marketingapp.register.signin.SignInButton
import com.example.marketingapp.register.signin.SignInTextField
import kotlin.math.log

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val loginResponse by viewModel.loginResponse.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val loginError by viewModel.loginError.observeAsState()

    val tokenManager = TokenManager(context)

    LaunchedEffect(loginResponse) {
        loginResponse?.token?.let {
            tokenManager.saveToken(loginResponse!!.token ?: "")
            Log.d("TAG", "LoginScreen: ${tokenManager.getToken()}")
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
            // Navigate to HomeScreen or another activity
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            (context as? Activity)?.finish()
        }
    }
    Log.d("TAG", "LoginScreen: Token: ${tokenManager.getToken()}")

    LaunchedEffect(loginError) {
        loginError?.let {
            Toast.makeText(context, "Invalid email or password", Toast.LENGTH_LONG).show()
            viewModel.clearLoginError()
        }
    }
    // Handle back press to exit the app
    BackHandler {
        ActivityCompat.finishAffinity(context as Activity)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LoginContent(viewModel, isLoading)
            LoginBottomBar(navController)
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        colorResource(id = R.color.black).copy(alpha = 0.5f),
                        shape = RoundedCornerShape(Dimension.MediumPadding)
                    )
                    .padding(Dimension.MediumPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.green), // Set loading spinner color to green
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}


@Composable
fun LoginContent(viewModel: LoginViewModel, isLoading: Boolean) {
    Spacer(modifier = Modifier.height(Dimension.LargePadding))
    Column {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        val emailError by viewModel.emailError.collectAsState()
        val passwordError by viewModel.passwordError.collectAsState()

        LoginHeader()

        SignInTextField(
            text = email,
            onTextChange = { newText -> email = newText },
            label = stringResource(id = R.string.email_address),
            maxLines = 2,
            icon = R.drawable.ic_email,
            modifier = Modifier,
            isPassword = false,
            error = emailError
        )

        SignInTextField(
            text = password,
            onTextChange = { newText -> password = newText },
            label = stringResource(id = R.string.password),
            maxLines = 2,
            icon = R.drawable.ic_password,
            modifier = Modifier,
            isPassword = true,
            error = passwordError
        )

        SignInButton(
            text = stringResource(id = R.string.login),
            modifier = Modifier,
            onClick = {
                val login = LoginRequest(
                    email = email,
                    password = password
                )
                viewModel.login(login)
            }
        )
    }
}

@Composable
fun LoginHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimension.LargePadding,
                end = Dimension.LargePadding,
                top = Dimension.biggestLargePadding,
                bottom = Dimension.MediumPadding
            )
    ) {
        Text(
            text = stringResource(id = R.string.welcome_back),
            style = TextStyle(
                fontSize = Dimension.LargeFontSize,
                color = colorResource(id = R.color.green),
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = stringResource(id = R.string.enter_your_credentials_to_continue),
            style = TextStyle(
                fontSize = Dimension.MediumFontSize,
                color = colorResource(id = R.color.gray)
            )
        )
    }
}

@Composable
fun LoginBottomBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.bottom_bar))
            .padding(top = Dimension.LargePadding, bottom = Dimension.LargePadding)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Text(
                    text = stringResource(id = R.string.do_not_have_an_account),
                    style = TextStyle(
                        fontSize = Dimension.SmallFontSize,
                        color = colorResource(id = R.color.gray)
                    )
                )

                Spacer(modifier = Modifier.width(Dimension.SmallSpace))

                Text(
                    text = stringResource(id = R.string.register),
                    style = TextStyle(
                        fontSize = Dimension.SmallFontSize,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.green),
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.clickable {
                        navController.navigate("sign_in")
                    }
                )
            }
        }
    }
}
