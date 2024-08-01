package com.example.marketingapp.di

import com.example.domain.repository.register.LoginRepo
import com.example.domain.usecases.register.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUseCase(loginRepo: LoginRepo): LoginUseCase {
        return LoginUseCase(loginRepo)
    }
}
