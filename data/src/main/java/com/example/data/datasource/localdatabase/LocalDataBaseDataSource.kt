package com.example.data.datasource.localdatabase

import com.example.domain.datamodel.getallproperty.DataItem

interface LocalDataBaseDataSource {
    // Updated method to accept a list of image URLs
    suspend fun insertProperty(
        id: Long,
        area: String,
        price: String,
        propertyType: String,
        images: List<String>, // Updated to a list of image URLs
        description: String,
        location: String,
        title: String,
        listedAt: String,
        status: String,
    )

    // Method to retrieve all properties
    suspend fun getAllProperties(): List<DataItem>

    // Method to delete a property by its ID
    fun deletePropertyById(propertyId: Long)

    // Method to check if a property is marked as a favorite
    suspend fun isPropertyFavourite(propertyId: Long): Boolean
}