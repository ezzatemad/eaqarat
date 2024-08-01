package com.example.data.datasource.register

import com.example.domain.datamodel.register.login.LoginRequest
import com.example.domain.datamodel.register.login.LoginResponse

interface LoginDataSource {

    suspend fun login(loginRequest: LoginRequest): LoginResponse

}