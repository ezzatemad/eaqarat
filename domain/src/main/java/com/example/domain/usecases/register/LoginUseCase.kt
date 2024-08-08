package com.example.domain.usecases.register

import android.util.Log
import com.example.domain.datamodel.register.login.LoginRequest
import com.example.domain.datamodel.register.login.LoginResponse
import com.example.domain.repository.register.LoginRepo
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepo: LoginRepo
) {

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return try {
            loginRepo.login(loginRequest)
        } catch (ex: Exception) {
            throw ex
        }
    }
}