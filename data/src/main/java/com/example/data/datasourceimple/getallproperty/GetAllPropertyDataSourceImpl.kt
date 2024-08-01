package com.example.data.datasourceimple.getallproperty

import android.util.Log
import com.example.data.apiservice.getallproperty.PropertyApiService
import com.example.data.datasource.getallproperty.GetAllPropertyDataSource
import com.example.domain.datamodel.getallproperty.PropertyResponse
import javax.inject.Inject

class GetAllPropertyDataSourceImpl @Inject constructor(
    private val propertyApiService: PropertyApiService
) : GetAllPropertyDataSource {
    override suspend fun getAllProperty(token: String): PropertyResponse {
        Log.d("GetAllPropertyDataSourceImpl", "Making network request with token: $token")
        return propertyApiService.getAllProperty(token)
    }
}
