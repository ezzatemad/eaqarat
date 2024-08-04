package com.example.data.di.repository

import com.example.data.repositoryimpl.filterproperty.GetFilterPropertyRepoImpl
import com.example.data.repositoryimpl.getallpropert.GetAllPropertyRepoImpl
import com.example.data.repositoryimpl.register.LoginRepoImpl
import com.example.data.repositoryimpl.register.SignInRepoImpl
import com.example.data.repositoryimpl.search.SearchRepoImpl
import com.example.data.repositoryimpl.sortproperty.SortRepoImpl
import com.example.domain.repository.filterproperty.GetFilterPropertyRepo
import com.example.domain.repository.getallproperty.GetAllPropertyRepo
import com.example.domain.repository.register.LoginRepo
import com.example.domain.repository.register.SignInRepo
import com.example.domain.repository.search.SearchRepo
import com.example.domain.repository.sort.SortRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideGetSortRepository(sortRepoImpl: SortRepoImpl): SortRepo


    @Binds
    abstract fun provideGetSearchRepository(searchRepoImpl: SearchRepoImpl): SearchRepo


    @Binds
    abstract fun provideGetFilterPropertyRepository(getFilterPropertyRepoImpl: GetFilterPropertyRepoImpl): GetFilterPropertyRepo


    @Binds
    abstract fun provideGetAllPropertyRepository(getAllPropertyRepoImpl: GetAllPropertyRepoImpl): GetAllPropertyRepo


    @Binds
    abstract fun provideSignInRepository(signInRepoImpl: SignInRepoImpl): SignInRepo

    @Binds
    abstract fun provideLoginRepository(loginRepoImpl: LoginRepoImpl): LoginRepo
}