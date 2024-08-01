package com.example.marketingapp.register.signin

sealed class SignInEvent {
    data class SubmitSignIn(val username: String, val password: String) : SignInEvent()
    data class ShowErrorMessage(val message: String) : SignInEvent()
}
