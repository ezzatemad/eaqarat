package com.example.data.datasource.register

import com.example.domain.datamodel.register.signin.RegisterResponse
import com.example.domain.datamodel.register.signin.User

interface SignInDataSource {

    suspend fun signIn(user: User): RegisterResponse
}