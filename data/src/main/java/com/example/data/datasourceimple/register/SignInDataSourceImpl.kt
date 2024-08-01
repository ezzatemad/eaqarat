package com.example.data.datasourceimple.register

import com.example.data.apiservice.register.RegisterApiService
import com.example.data.datasource.register.SignInDataSource
import com.example.domain.datamodel.register.signin.RegisterResponse
import com.example.domain.datamodel.register.signin.User
import javax.inject.Inject

class SignInDataSourceImpl @Inject constructor(
    private val apiService: RegisterApiService
) : SignInDataSource {
    override suspend fun signIn(user: User): RegisterResponse {
        return apiService.registerUser(user)
    }
}