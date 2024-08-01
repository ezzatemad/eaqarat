package com.example.data.datasourceimple.register

import com.example.data.apiservice.register.RegisterApiService
import com.example.data.datasource.register.LoginDataSource
import com.example.domain.datamodel.register.login.LoginRequest
import com.example.domain.datamodel.register.login.LoginResponse
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val apiService: RegisterApiService
) : LoginDataSource {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return apiService.loginUser(loginRequest)
    }
}