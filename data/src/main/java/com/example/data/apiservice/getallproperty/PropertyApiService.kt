package com.example.data.apiservice.getallproperty

import com.example.domain.datamodel.getallproperty.PropertyResponse
import retrofit2.http.GET
import retrofit2.http.Header


interface PropertyApiService {

    @GET("api/Property")
    suspend fun getAllProperty(
        @Header("Authorization") token: String
    ): PropertyResponse
}