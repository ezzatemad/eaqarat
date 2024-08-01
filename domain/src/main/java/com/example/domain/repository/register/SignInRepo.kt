package com.example.domain.repository.register

import com.example.domain.datamodel.register.signin.RegisterResponse
import com.example.domain.datamodel.register.signin.User

interface SignInRepo {

    suspend fun signIn(user: User): RegisterResponse
}