package com.example.data.repositoryimpl.register

import com.example.data.datasource.register.SignInDataSource
import com.example.domain.datamodel.register.signin.RegisterResponse
import com.example.domain.datamodel.register.signin.User
import com.example.domain.repository.register.SignInRepo
import javax.inject.Inject

class SignInRepoImpl @Inject constructor(
    private val signInDataSource: SignInDataSource
) : SignInRepo {
    override suspend fun signIn(user: User): RegisterResponse {
        return signInDataSource.signIn(user)
    }
}