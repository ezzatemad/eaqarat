package com.example.data.apiservice.filterproperty

import com.example.domain.datamodel.filterproperty.FilterPropertyResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FilterPropertyApiService {

    @GET("api/Property/filter")
    suspend fun getFilterProperty(
        @Header("Authorization") token: String,
        @Query("title") title: String,
        @Query("propertyType") propertyType: String
    ): FilterPropertyResponse
}