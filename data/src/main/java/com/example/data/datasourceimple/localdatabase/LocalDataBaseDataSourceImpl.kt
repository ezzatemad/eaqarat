package com.example.data.datasourceimple.localdatabase

import android.util.Log
import com.example.data.PropertyItem
import com.example.data.datasource.localdatabase.LocalDataBaseDataSource
import com.example.data.model.FavouriteItem
import com.example.domain.datamodel.getallproperty.DataItem
import javax.inject.Inject

class LocalDataBaseDataSourceImpl @Inject constructor(
    private val propertyItem: PropertyItem
) : LocalDataBaseDataSource {

    override suspend fun insertProperty(
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
    ) {
        try {
            propertyItem.propertyItemQueries.insertDataItem(
                id,
                area,
                price,
                propertyType,
                imageUrl,
                description,
                location,
                title,
                listedAt,
                status
            )

        } catch (ex: Exception) {
            Log.d("TAG", "insertTask: @${ex.localizedMessage}")
        }
    }

    override suspend fun getAllProperties(): List<DataItem> {
        return propertyItem.propertyItemQueries.selectAllDataItems().executeAsList()
            .map { propertyDB ->
                DataItem(
                    id = propertyDB.id,
                    area = propertyDB.area?.toDouble(),
                    propertyType = propertyDB.propertyType,
                    price = propertyDB.price?.toDouble(),
                    imageUrl = emptyList(),
                    status = propertyDB.status,
                    description = propertyDB.description,
                    title = propertyDB.title,
                    location = propertyDB.location,
                    listedAt = propertyDB.listedAt,
                )
            }
    }

    override suspend fun deletePropertyById(propertyId: Long) {
        try {
            propertyItem.propertyItemQueries.deleteDataItemById(propertyId)
        } catch (ex: Exception){
            Log.d("TAG", "deletePropertyById: ${ex.localizedMessage}")
        }
    }

    override suspend fun isPropertyFavourite(propertyId: Long): Boolean {
        return try {
            val result = propertyItem.propertyItemQueries.selectDataItemById(propertyId).executeAsOneOrNull()
            result != null
        } catch (ex: Exception) {
            Log.d("TAG", "isPropertyFavourite: ${ex.localizedMessage}")
            false
        }
    }
}