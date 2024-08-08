package com.example.domain.repository.localdatabase

import com.example.domain.datamodel.getallproperty.DataItem

interface LocalDataBaseRepo {
    suspend fun insertProperty(
        id: Long,
        area: String,
        price: String,
        propertyType: String,
        imageUrl: String,
        description: String,
        location: String,
        title: String,
        listedAt: String,
        status: String
    )

    suspend fun getAllProperty(): List<DataItem>

    suspend fun deletePropertyById(propertyId: Long)


    suspend fun isPropertyFavourite(propertyId: Long): Boolean
}