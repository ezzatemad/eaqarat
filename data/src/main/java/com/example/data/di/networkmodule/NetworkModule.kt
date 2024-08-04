package com.example.data.di.networkmodule

import android.content.Context
import com.example.data.apiservice.filterproperty.FilterPropertyApiService
import com.example.data.apiservice.getallproperty.PropertyApiService
import com.example.data.apiservice.register.RegisterApiService
import com.example.data.apiservice.search.SearchApiService
import com.example.data.apiservice.sortproperty.SortPropertyApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGetSortApiService(
        retrofit: Retrofit
    ): SortPropertyApiService {
        return retrofit.create(SortPropertyApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetSearchApiService(
        retrofit: Retrofit
    ): SearchApiService {
        return retrofit.create(SearchApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetFilterPropertyApiService(
        retrofit: Retrofit
    ): FilterPropertyApiService {
        return retrofit.create(FilterPropertyApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideGetAllPropertyApiService(
        retrofit: Retrofit
    ): PropertyApiService {
        return retrofit.create(PropertyApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideRegisterApiService(
        retrofit: Retrofit
    ): RegisterApiService {
        return retrofit.create(RegisterApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://propertybydopaminegroup.runasp.net/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()


    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()


    @Provides
    @Singleton
    fun provideAppContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}