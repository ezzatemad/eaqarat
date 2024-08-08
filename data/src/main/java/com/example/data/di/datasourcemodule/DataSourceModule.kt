package com.example.data.di.datasourcemodule

import com.example.data.datasource.filterproperty.GetFilterPropertyDataSource
import com.example.data.datasource.getallproperty.GetAllPropertyDataSource
import com.example.data.datasource.localdatabase.LocalDataBaseDataSource
import com.example.data.datasource.register.LoginDataSource
import com.example.data.datasource.register.SignInDataSource
import com.example.data.datasource.search.SearchDataSource
import com.example.data.datasource.sortproperty.SortPropertyDataSource
import com.example.data.datasourceimple.filterproperty.GetFilterProperty.getFilterPropertyDataSourceImple
import com.example.data.datasourceimple.getallproperty.GetAllPropertyDataSourceImpl
import com.example.data.datasourceimple.localdatabase.LocalDataBaseDataSourceImpl
import com.example.data.datasourceimple.register.LoginDataSourceImpl
import com.example.data.datasourceimple.register.SignInDataSourceImpl
import com.example.data.datasourceimple.search.SearchDataSourceImpl
import com.example.data.datasourceimple.sort.SortDataSourceImpl
import com.example.data.repositoryimpl.localdatabase.LocalDataBaseRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideLocalDataBaseDataSource(
        localDataBaseDataSourceImpl: LocalDataBaseDataSourceImpl
    ): LocalDataBaseDataSource


    @Binds
    abstract fun provideGetSortDataSource(
        sortDataSourceImpl: SortDataSourceImpl
    ): SortPropertyDataSource


    @Binds
    abstract fun provideGetSearchDataSource(
        searchDataSourceImpl: SearchDataSourceImpl
    ): SearchDataSource


    @Binds
    abstract fun provideGetFilterPropertyDataSource(
        getFilterPropertyDataSourceImple: getFilterPropertyDataSourceImple
    ): GetFilterPropertyDataSource


    @Binds
    abstract fun provideGetAllPropertyDataSource(
        getAllPropertyDataSourceImpl: GetAllPropertyDataSourceImpl
    ): GetAllPropertyDataSource


    @Binds
    abstract fun provideSignInDataSource(
        signInDataSourceImpl: SignInDataSourceImpl
    ): SignInDataSource


    @Binds
    abstract fun provideLoginDataSource(
        loginDataSourceImpl: LoginDataSourceImpl
    ): LoginDataSource
}
