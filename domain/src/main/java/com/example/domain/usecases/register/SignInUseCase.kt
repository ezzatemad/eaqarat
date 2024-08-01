package com.example.domain.usecases.register

import com.example.domain.datamodel.register.signin.RegisterResponse
import com.example.domain.datamodel.register.signin.User
import com.example.domain.repository.register.SignInRepo
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val signInRepo: SignInRepo
) {
    suspend fun signIn(user: User): RegisterResponse {
        return signInRepo.signIn(user)
    }
}