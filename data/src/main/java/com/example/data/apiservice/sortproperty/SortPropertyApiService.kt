package com.example.data.apiservice.sortproperty

import com.example.domain.datamodel.sortproperty.SortResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SortPropertyApiService {

    @GET("api/Property/sort")
    suspend fun getSortedProperties(
        @Header("Authorization") token: String,
        @Query("sortBy") sortBy: String,
        @Query("ascending") ascending: Boolean
    ): SortResponse
}