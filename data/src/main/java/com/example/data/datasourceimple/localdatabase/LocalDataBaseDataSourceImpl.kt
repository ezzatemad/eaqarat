package com.example.data.datasourceimple.localdatabase

import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.data.PropertyItem
import com.example.data.datasource.localdatabase.LocalDataBaseDataSource
import com.example.domain.datamodel.getallproperty.DataItem
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class LocalDataBaseDataSourceImpl @Inject constructor(
    private val propertyItem: PropertyItem,
) : LocalDataBaseDataSource {

    val currentTime = System.currentTimeMillis()

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
        Log.d("TAG", "insertProperty: $currentTime")
        try {
            propertyItem.propertyItemQueries.insertDataItem(
                id,
                area,
                price,
                propertyType,
                description,
                location,
                title,
                listedAt,
                status,
            )

            images.forEach { imageUrl ->
                propertyItem.propertyItemQueries.InsertImages(
                    property_id = id,
                    imageUrl = imageUrl
                )
            }

        } catch (ex: Exception) {
            Log.d("TAG", "insertProperty: ${ex.localizedMessage}")
        }
    }




    override suspend fun getAllProperties(): List<DataItem> {
        return propertyItem.propertyItemQueries.selectAllDataItems().executeAsList()
            .map { propertyDB ->
                // Retrieve images for the property
                val images =
                    propertyItem.propertyItemQueries.selectImagesByPropertyId(propertyDB.id)
                        .executeAsList()
                        .map { it.imageUrl }

                DataItem(
                    id = propertyDB.id,
                    area = propertyDB.area?.toDouble(),
                    propertyType = propertyDB.propertyType,
                    price = propertyDB.price?.toDouble(),
                    imageUrl = images,
                    status = propertyDB.status,
                    description = propertyDB.description,
                    title = propertyDB.title,
                    location = propertyDB.location,
                    listedAt = propertyDB.listedAt,
                )
            }
    }

    override fun deletePropertyById(propertyId: Long) {
        try {
            // Delete images associated with the property
            propertyItem.propertyItemQueries.deleteImagesByPropertyId(propertyId)

            // Delete the property
            propertyItem.propertyItemQueries.deleteDataItemById(propertyId)
        } catch (ex: Exception) {
            Log.d("TAG", "deletePropertyById: ${ex.localizedMessage}")
        }
    }

    override suspend fun isPropertyFavourite(propertyId: Long): Boolean {
        return try {
            val result =
                propertyItem.propertyItemQueries.selectDataItemById(propertyId).executeAsOneOrNull()
            result != null
        } catch (ex: Exception) {
            Log.d("TAG", "isPropertyFavourite: ${ex.localizedMessage}")
            false
        }
    }
}