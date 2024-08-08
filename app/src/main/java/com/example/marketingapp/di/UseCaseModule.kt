package com.example.marketingapp.di

import com.example.domain.repository.filterproperty.GetFilterPropertyRepo
import com.example.domain.repository.getallproperty.GetAllPropertyRepo
import com.example.domain.repository.localdatabase.LocalDataBaseRepo
import com.example.domain.repository.register.LoginRepo
import com.example.domain.repository.search.SearchRepo
import com.example.domain.repository.sort.SortRepo
import com.example.domain.usecases.filterproperty.GetFilterPropertyUseCase
import com.example.domain.usecases.getallproperty.GetAllPropertyUseCase
import com.example.domain.usecases.locadatabase.LocalDataBaseUseCase
import com.example.domain.usecases.register.LoginUseCase
import com.example.domain.usecases.search.SearchUseCase
import com.example.domain.usecases.sortproperty.SortUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetLocalDataBaseUseCase(localDataBaseRepo: LocalDataBaseRepo): LocalDataBaseUseCase {
        return LocalDataBaseUseCase(localDataBaseRepo)
    }

    @Provides
    fun provideGetSortUseCase(sortRepo: SortRepo): SortUseCase {
        return SortUseCase(sortRepo)
    }

    @Provides
    fun provideGetSearchUseCase(searchRepo: SearchRepo): SearchUseCase {
        return SearchUseCase(searchRepo)
    }

    @Provides
    fun provideGetFilterPropertyUseCase(getFilterPropertyRepo: GetFilterPropertyRepo): GetFilterPropertyUseCase {
        return GetFilterPropertyUseCase(getFilterPropertyRepo)
    }

    @Provides
    fun provideGetAllPropertyUseCase(getAllPropertyRepo: GetAllPropertyRepo): GetAllPropertyUseCase {
        return GetAllPropertyUseCase(getAllPropertyRepo)
    }

    @Provides
    fun provideLoginUseCase(loginRepo: LoginRepo): LoginUseCase {
        return LoginUseCase(loginRepo)
    }
}