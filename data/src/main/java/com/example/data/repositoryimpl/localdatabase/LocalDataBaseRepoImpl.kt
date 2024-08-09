package com.example.data.repositoryimpl.localdatabase

import android.util.Log
import com.example.data.datasource.localdatabase.LocalDataBaseDataSource
import com.example.domain.datamodel.getallproperty.DataItem
import com.example.domain.repository.localdatabase.LocalDataBaseRepo
import javax.inject.Inject

class LocalDataBaseRepoImpl @Inject constructor(
    private val localDataBaseDataSource: LocalDataBaseDataSource
) : LocalDataBaseRepo {

    override suspend fun insertProperty(
        id: Long,
        area: String,
        price: String,
        propertyType: String,
        images: List<String>,
        description: String,
        location: String,
        title: String,
        listedAt: String,
        status: String
    ) {
        try {
            // Insert property details
            localDataBaseDataSource.insertProperty(
                id,
                area,
                price,
                propertyType,
                images, // Pass the list of images
                description,
                location,
                title,
                listedAt,
                status
            )
        } catch (ex: Exception) {
            Log.d("TAG", "insertProperty: ${ex.localizedMessage}")
        }
    }

    override suspend fun getAllProperty(): List<DataItem> {
        return localDataBaseDataSource.getAllProperties()
    }

    override suspend fun deletePropertyById(propertyId: Long) {
        try {
            // Delete the property and its associated images
            localDataBaseDataSource.deletePropertyById(propertyId)
        } catch (ex: Exception) {
            Log.d("TAG", "deletePropertyById: ${ex.localizedMessage}")
        }
    }

    override suspend fun isPropertyFavourite(propertyId: Long): Boolean {
        return try {
            localDataBaseDataSource.isPropertyFavourite(propertyId)
        } catch (ex: Exception) {
            Log.d("TAG", "isPropertyFavourite: ${ex.localizedMessage}")
            false
        }
    }
}
