package com.example.marketingapp.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.example.marketingapp.Dimension
import com.example.marketingapp.R

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LoginContent()
            LoginTerms(onTermsClicked = {

            },
                onPrivacyPolicyClicked = {

                }
            )
            LoginBottomBar()
        }
    }
}

@Composable
fun LoginContent() {
    Spacer(modifier = Modifier.height(Dimension.LargePadding))
    Column(
        modifier = Modifier.padding(top = Dimension.LargePadding)
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        LoginHeader()

        SignInTextField(
            text = email,
            onTextChange = { newText -> email = newText },
            label = "Email Address",
            maxLines = 2,
            icon = R.drawable.ic_email,
            modifier = Modifier,
            isPassword = false

        )
        SignInTextField(
            text = password,
            onTextChange = { newText -> password = newText },
            label = "Password",
            maxLines = 2,
            icon = R.drawable.ic_password,
            modifier = Modifier,
            isPassword = true
        )

        Text(
            text = stringResource(R.string.forget_password),
            style = TextStyle(
                fontSize = Dimension.MediumFontSize,
                color = colorResource(id = R.color.green)
            ),
            modifier = Modifier.padding(
                start = Dimension.LargePadding,
                end = Dimension.LargePadding,
                top = Dimension.MediumPadding,
                bottom = Dimension.MediumPadding
            )
        )

        SignInButton(text = stringResource(id = R.string.log_in), modifier = Modifier)

    }
}

@Composable
fun LoginTerms(onTermsClicked: () -> Unit, onPrivacyPolicyClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Dimension.LargePadding, end = Dimension.LargePadding),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = colorResource(id = R.color.gray))) {
                        append(stringResource(R.string.by_logging_you_are_agreeing_with_our))
                    }
                    pushStyle(
                        SpanStyle(
                            color = colorResource(id = R.color.green),
                            textDecoration = TextDecoration.Underline
                        )
                    )
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.green),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(stringResource(R.string.terms_of_use))
                    }
                    pop()
                    withStyle(style = SpanStyle(color = colorResource(id = R.color.gray))) {
                        append(stringResource(R.string.and))
                    }
                    pushStyle(
                        SpanStyle(
                            color = colorResource(id = R.color.green),
                            textDecoration = TextDecoration.Underline
                        )
                    )
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.green),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(stringResource(R.string.privacy_policy))
                    }
                    pop()
                },
                onClick = { offset ->
                    // Define clickable ranges based on text length
                    val termsStart = 38
                    val termsEnd = 54
                    val privacyPolicyStart = 60
                    val privacyPolicyEnd = 79

                    when {
                        offset in termsStart..termsEnd -> onTermsClicked()
                        offset in privacyPolicyStart..privacyPolicyEnd -> onPrivacyPolicyClicked()
                    }
                },
                modifier = Modifier.padding(end = Dimension.LargePadding)
            )
        }
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
fun LoginBottomBar() {
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
                    )
                )
            }
        }
    }
}
