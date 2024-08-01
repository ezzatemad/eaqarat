package com.example.data.di.repository

import com.example.data.repositoryimpl.getallpropert.GetAllPropertyRepoImpl
import com.example.data.repositoryimpl.register.LoginRepoImpl
import com.example.data.repositoryimpl.register.SignInRepoImpl
import com.example.domain.repository.getallproperty.GetAllPropertyRepo
import com.example.domain.repository.register.LoginRepo
import com.example.domain.repository.register.SignInRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideGetAllPropertyRepository(getAllPropertyRepoImpl: GetAllPropertyRepoImpl): GetAllPropertyRepo


    @Binds
    abstract fun provideSignInRepository(signInRepoImpl: SignInRepoImpl): SignInRepo

    @Binds
    abstract fun provideLoginRepository(loginRepoImpl: LoginRepoImpl): LoginRepo
}