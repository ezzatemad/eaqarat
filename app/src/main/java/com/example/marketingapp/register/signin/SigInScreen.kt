package com.example.marketingapp.register.signin

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.datamodel.register.signin.User
import com.example.marketingapp.Dimension
import com.example.marketingapp.R


@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val registerResponse by viewModel.registerResponse.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val phoneError by viewModel.phoneError.collectAsState()
    val registerError by viewModel.registerError.observeAsState()

    // Handle back press to exit the app
    BackHandler {
        ActivityCompat.finishAffinity(context as Activity)
    }

    LaunchedEffect(registerResponse) {
        if (registerResponse != null) {
            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
            navController.navigate("login") {
                popUpTo("signin") { inclusive = true }
            }
        }
    }

    LaunchedEffect(registerError) {
        registerError?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SignInContent(viewModel, isLoading)
            SignInBottomBar(navController)
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                    .padding(Dimension.LargePadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.green),
                    strokeWidth = 4.dp
                )
            }
        }
    }
}


@Composable
fun SignInContent(viewModel: SignInViewModel, isLoading: Boolean) {
    Spacer(modifier = Modifier.height(Dimension.LargePadding))
    Column {
        val context = LocalContext.current
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var isChecked by remember { mutableStateOf(false) }

        val nameError by viewModel.nameError.collectAsState()
        val emailError by viewModel.emailError.collectAsState()
        val phoneError by viewModel.phoneError.collectAsState()
        val passwordError by viewModel.passwordError.collectAsState()
        var confirmPasswordError by remember { mutableStateOf<String?>(null) }

        SignInHeader()

        SignInTextField(
            text = name,
            onTextChange = { newText -> name = newText },
            label = stringResource(id = R.string.name),
            maxLines = 2,
            icon = R.drawable.ic_user,
            modifier = Modifier,
            isPassword = false,
            error = nameError
        )
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
            text = phone,
            onTextChange = { newText -> phone = newText },
            label = stringResource(id = R.string.phone),
            maxLines = 1,
            icon = R.drawable.ic_email,
            modifier = Modifier,
            isPassword = false,
            error = phoneError
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
        SignInTextField(
            text = confirmPassword,
            onTextChange = { newText -> confirmPassword = newText },
            label = stringResource(id = R.string.confirm_password),
            maxLines = 2,
            icon = R.drawable.ic_password,
            modifier = Modifier,
            isPassword = true,
            error = confirmPasswordError
        )

        SignInTerms(
            isChecked = isChecked,
            onCheckedChange = { isChecked = it },
            onTermsClicked = {
                // Handle terms clicked
            },
            onPrivacyPolicyClicked = {
                // Handle privacy policy clicked
            }
        )

        SignInButton(
            text = stringResource(id = R.string.register),
            modifier = Modifier,
            onClick = {
                confirmPasswordError = null

                if (isChecked) {
                    if (password != confirmPassword) {
                        confirmPasswordError = context.getString(R.string.passwords_do_not_match)
                    } else {
                        val user = User(
                            username = name,
                            email = email,
                            phone = phone,
                            password = password,
                        )
                        viewModel.signIn(user)
                    }
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.please_agree_to_the_terms_and_conditions),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
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
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(Dimension.MediumPadding),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
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
    isPassword: Boolean = false,
    error: String? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
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
                            painter = painterResource(id = if (passwordVisible) R.drawable.ic_hide_eye else R.drawable.ic_hide_eye),
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
                ),
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


@Composable
fun SignInBottomBar(navController: NavController) {
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
                    ),
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                    }
                )
            }
        }
    }
}

@Composable
fun SignInTerms(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onTermsClicked: () -> Unit,
    onPrivacyPolicyClicked: () -> Unit
) {
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
                onCheckedChange = onCheckedChange,
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
                        append(stringResource(R.string.by_registering_you_are_agreeing_with_our))
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
                        append(stringResource(id = R.string.terms_of_use))
                    }
                    pop()
                    withStyle(style = SpanStyle(color = colorResource(id = R.color.gray))) {
                        append(stringResource(id = R.string.and))
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
                        append(stringResource(id = R.string.privacy_policy))
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
fun RoleSpinner(selectedRole: String, onRoleSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val roles = listOf("Admin", "Customer")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimension.LargePadding,
                end = Dimension.LargePadding,
                bottom = Dimension.MediumPadding
            )
            .background(
                color = colorResource(id = R.color.text_field_background),
                shape = RoundedCornerShape(Dimension.SmallCornerRadius)
            )
            .clickable { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimension.MediumPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selectedRole.isEmpty()) stringResource(R.string.select_role) else selectedRole,
                style = TextStyle(
                    color = if (selectedRole.isEmpty()) colorResource(id = R.color.text_field_unfocused) else Color.Black,
                    fontSize = Dimension.MediumFontSize
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
                contentDescription = null,
                tint = colorResource(id = R.color.green)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            roles.forEach { role ->
                DropdownMenuItem(
                    onClick = {
                        onRoleSelected(role)
                        expanded = false
                    },
                    text = {
                        Text(text = role)
                    }
                )
            }
        }
    }
}