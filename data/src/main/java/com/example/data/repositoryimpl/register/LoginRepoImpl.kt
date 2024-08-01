package com.example.data.repositoryimpl.register

import com.example.data.datasource.register.LoginDataSource
import com.example.domain.datamodel.register.login.LoginRequest
import com.example.domain.datamodel.register.login.LoginResponse
import com.example.domain.repository.register.LoginRepo
import javax.inject.Inject

class LoginRepoImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginRepo {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return loginDataSource.login(loginRequest)
    }
}