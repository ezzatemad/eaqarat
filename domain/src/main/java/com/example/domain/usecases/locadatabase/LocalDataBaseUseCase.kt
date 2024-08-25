package com.example.domain.usecases.locadatabase

import android.util.Log
import com.example.domain.datamodel.getallproperty.DataItem
import com.example.domain.repository.localdatabase.LocalDataBaseRepo
import javax.inject.Inject

class LocalDataBaseUseCase @Inject constructor(
    private val localDataBaseRepo: LocalDataBaseRepo
) {
    suspend fun insertProperty(
        id: Long,
        area: String,
        price: String,
        propertyType: String,
        images: List<String>, // Updated to accept a list of image URLs
        description: String,
        location: String,
        title: String,
        listedAt: String,
        status: String,
    ) {
        try {
            localDataBaseRepo.insertProperty(
                id,
                area,
                price,
                propertyType,
                images, // Pass the list of images
                description,
                location,
                title,
                listedAt,
                status,
            )
        } catch (ex: Exception) {
            Log.d("TAG", "insertProperty: ${ex.localizedMessage}")
        }
    }

    suspend fun getAllProperty(): List<DataItem> {
        return try {
            localDataBaseRepo.getAllProperty()
        } catch (ex: Exception) {
            Log.d("TAG", "getAllProperty: ${ex.localizedMessage}")
            emptyList() // Return an empty list or handle as needed
        }
    }

    fun deletePropertyById(propertyId: Long) {
        try {
            localDataBaseRepo.deletePropertyById(propertyId)
        } catch (ex: Exception) {
            Log.d("TAG", "deletePropertyById: ${ex.localizedMessage}")
        }
    }

    suspend fun isPropertyFavourite(propertyId: Long): Boolean {
        return try {
            localDataBaseRepo.isPropertyFavourite(propertyId)
        } catch (ex: Exception) {
            Log.d("TAG", "isPropertyFavourite: ${ex.localizedMessage}")
            false
        }
    }
}