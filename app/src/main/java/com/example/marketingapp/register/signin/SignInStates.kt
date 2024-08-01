package com.example.marketingapp.register.signin

import com.example.domain.datamodel.register.signin.User

sealed class SignInStates {
    object Idle : SignInStates()
    object Loading : SignInStates()
    data class Success(val user: User) : SignInStates()
    data class Error(val message: String) : SignInStates()
}
