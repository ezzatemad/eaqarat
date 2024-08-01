package com.example.domain.repository.register

import com.example.domain.datamodel.register.login.LoginRequest
import com.example.domain.datamodel.register.login.LoginResponse

interface LoginRepo {

    suspend fun login(loginRequest: LoginRequest): LoginResponse
}