package com.example.data.apiservice.getallproperty

import com.example.domain.repository.getallproperty.PropertyResponse
import retrofit2.http.GET


interface PropertyApiService {

    @GET()
    suspend fun getAllProperty(): PropertyResponse

}