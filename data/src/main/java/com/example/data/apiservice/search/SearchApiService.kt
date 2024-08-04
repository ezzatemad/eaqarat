package com.example.data.apiservice.search

import com.example.domain.datamodel.searchproperty.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchApiService {

    @GET("api/Property/search")
    suspend fun getAllProperty(
        @Header("Authorization") token: String,
        @Query("query") query: String
    ): SearchResponse
}