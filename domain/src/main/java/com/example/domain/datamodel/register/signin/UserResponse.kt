package com.example.domain.datamodel.register.signin

data class RegisterResponse(
    val statusCode: Int,
    val message: String,
    val data: UserData,
    val timeStamp: String
)

data class UserData(
    val userId: Int,
    val username: String,
    val email: String,
    val password: String,
    val phone: String,
    val role: String? = "Customer",
    val createdAt: String
)