package com.example.domain.repository.localdatabase

import com.example.domain.datamodel.getallproperty.DataItem

interface LocalDataBaseRepo {
    suspend fun insertProperty(
        id: Long,
        area: String,
        price: String,
        propertyType: String,
        images: List<String>, // Updated from String to List<String>
        description: String,
        location: String,
        title: String,
        listedAt: String,
        status: String
    )

    suspend fun getAllProperty(): List<DataItem>

    fun deletePropertyById(propertyId: Long)

    suspend fun isPropertyFavourite(propertyId: Long): Boolean
}