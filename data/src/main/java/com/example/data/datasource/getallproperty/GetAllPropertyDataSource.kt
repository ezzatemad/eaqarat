package com.example.data.datasource.getallproperty

import com.example.domain.datamodel.getallproperty.PropertyResponse

interface GetAllPropertyDataSource {

    suspend fun getAllProperty(token: String): PropertyResponse
}