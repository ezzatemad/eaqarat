package com.example.data.di.datasourcemodule

import com.example.data.datasource.register.LoginDataSource
import com.example.data.datasource.register.SignInDataSource
import com.example.data.datasourceimple.register.LoginDataSourceImpl
import com.example.data.datasourceimple.register.SignInDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideSignInDataSource(
        signInDataSourceImpl: SignInDataSourceImpl
    ): SignInDataSource


    @Binds
    abstract fun provideLoginDataSource(
        loginDataSourceImpl: LoginDataSourceImpl
    ): LoginDataSource
}
