package com.example.domain.datamodel.register.signin

data class User(
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String? = "Customer"
)
