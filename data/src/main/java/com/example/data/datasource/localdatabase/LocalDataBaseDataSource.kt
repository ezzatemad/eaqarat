package com.example.data.datasource.localdatabase

import com.example.domain.datamodel.getallproperty.DataItem

interface LocalDataBaseDataSource {
    suspend fun insertProperty(
        id:Long,
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

    suspend fun getAllProperties(): List<DataItem>

    suspend fun deletePropertyById(propertyId: Long)

    suspend fun isPropertyFavourite(propertyId: Long): Boolean

}