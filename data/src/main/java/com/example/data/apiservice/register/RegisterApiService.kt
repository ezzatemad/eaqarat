package com.example.data.apiservice.register

import com.example.domain.datamodel.register.login.LoginRequest
import com.example.domain.datamodel.register.login.LoginResponse
import com.example.domain.datamodel.register.signin.RegisterResponse
import com.example.domain.datamodel.register.signin.User
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {

    @POST("api/User/register")
    suspend fun registerUser(@Body user: User): RegisterResponse

    @POST("api/User/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): LoginResponse
}