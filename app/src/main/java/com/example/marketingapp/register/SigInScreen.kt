package com.example.marketingapp.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import com.example.marketingapp.Dimension
import com.example.marketingapp.R


@Composable
fun SignInScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SignInContent()
            SignInBottomBar()
        }
    }
}


@Composable
fun SignInContent() {
    Spacer(modifier = Modifier.height(Dimension.LargePadding))
    Column(
        modifier = Modifier.padding(top = Dimension.LargePadding)
    ) {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        SignInHeader()

        SignInTextField(
            text = name,
            onTextChange = { newText -> name = newText },
            label = "Name",
            maxLines = 2,
            icon = R.drawable.ic_user,
            modifier = Modifier,
            isPassword = false
        )
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
        SignInTextField(
            text = confirmPassword,
            onTextChange = { newText -> confirmPassword = newText },
            label = "Confirm Password",
            maxLines = 2,
            icon = R.drawable.ic_password,
            modifier = Modifier,
            isPassword = true
        )

        Terms(onTermsClicked = {

        },
            onPrivacyPolicyClicked = {

            }
        )

        SignInButton(text = stringResource(id = R.string.register), modifier = Modifier)
    }
}


@Composable
fun SignInHeader() {
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
            text = stringResource(id = R.string.create_account),
            style = TextStyle(
                fontSize = Dimension.LargeFontSize,
                color = colorResource(id = R.color.green),
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = stringResource(id = R.string.register_to_get_started),
            style = TextStyle(
                fontSize = Dimension.MediumFontSize,
                color = colorResource(id = R.color.gray)
            )
        )
    }
}

@Composable
fun SignInButton(
    text: String,
    modifier: Modifier
) {
    Button(
        onClick = { },
        shape = RoundedCornerShape(Dimension.MediumPadding),
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green)),
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimension.LargePadding)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = Dimension.MediumFontSize,
                color = Color.White
            )
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier,
    label: String,
    maxLines: Int,
    icon: Int? = null,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text(text = label) },
        maxLines = maxLines,
        shape = RoundedCornerShape(Dimension.SmallCornerRadius),
        textStyle = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.ExtraLight,
            fontSize = Dimension.MediumFontSize
        ),
        leadingIcon = icon?.let {
            {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = colorResource(id = R.color.gray)
                )
            }
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = if (passwordVisible) R.drawable.ic_user else R.drawable.ic_email),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = colorResource(id = R.color.green)
                    )
                }
            }
        } else {
            null
        },
        visualTransformation = if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.text_field_border),
            unfocusedBorderColor = colorResource(id = R.color.text_field_border),
            containerColor = colorResource(id = R.color.text_field_background),
            unfocusedLabelColor = colorResource(id = R.color.text_field_unfocused),
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = colorResource(id = R.color.text_field_unfocused_text)
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = Dimension.LargePadding,
                end = Dimension.LargePadding,
                bottom = Dimension.MediumPadding
            ),
    )
}


@Composable
fun SignInBottomBar() {
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
                    text = stringResource(id = R.string.already_have_an_account),
                    style = TextStyle(
                        fontSize = Dimension.SmallFontSize,
                        color = colorResource(id = R.color.gray)
                    )
                )

                Spacer(modifier = Modifier.width(Dimension.SmallSpace))

                Text(
                    text = stringResource(id = R.string.log_in),
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

@Composable
fun Terms(onTermsClicked: () -> Unit, onPrivacyPolicyClicked: () -> Unit) {
    var isChecked by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimension.SmallPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(id = R.color.green),
                    uncheckedColor = colorResource(id = R.color.black),
                    checkmarkColor = Color.White
                )
            )
            Spacer(modifier = Modifier.width(Dimension.SmallSpace))
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = colorResource(id = R.color.gray))) {
                        append("By registering, you are agreeing with our ")
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
                        append("Terms of Use")
                    }
                    pop()
                    withStyle(style = SpanStyle(color = colorResource(id = R.color.gray))) {
                        append(" and ")
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
                        append("Privacy Policy")
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


