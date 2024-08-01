package com.example.domain.repository.getallproperty

import com.example.domain.datamodel.getallproperty.PropertyResponse

interface GetAllPropertyRepo {

    suspend fun getAllProperty(token: String): PropertyResponse
}