package com.example.marketingapp.favorite

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.domain.datamodel.getallproperty.DataItem
import com.example.domain.usecases.locadatabase.LocalDataBaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class FavouritePropertyViewModel @Inject constructor(
    private val localDataBaseUseCase: LocalDataBaseUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _isFavourite = MutableStateFlow(false)
    val isFavourite: StateFlow<Boolean> get() = _isFavourite

    private val _propertyList = MutableStateFlow<List<DataItem>>(emptyList())
    val propertyList: StateFlow<List<DataItem>> get() = _propertyList

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> get() = _snackbarMessage.asStateFlow()


    fun removeFavorite(property: DataItem) {
        viewModelScope.launch {
            localDataBaseUseCase.deletePropertyById(property.id!!) // Remove from database
            // Refresh the list
            getAllProperties()
        }
    }


    fun toggleFavouriteStatus(property: DataItem) {
        viewModelScope.launch {
            try {
                if (_isFavourite.value) {
                    // Remove from favorites
                    localDataBaseUseCase.deletePropertyById(property.id!!)
                    _isFavourite.value = false
                    _snackbarMessage.value = "Property removed from favorites"
                } else {
                    // Add to favorites
                    localDataBaseUseCase.insertProperty(
                        id = property.id!!,
                        area = property.area?.toString() ?: "",
                        price = property.price?.toString() ?: "",
                        propertyType = property.propertyType ?: "",
                        images = property.imageUrl?.filterNotNull()
                            ?: emptyList(), // Filter out nulls
                        description = property.description ?: "",
                        location = property.location ?: "",
                        title = property.title ?: "",
                        listedAt = property.listedAt ?: "",
                        status = property.status ?: "",
                    )
                    _isFavourite.value = true
                    _snackbarMessage.value = "Property added to favorites"

                }
            } catch (ex: Exception) {
                Log.e("tag", "Error toggling favorite status: ${ex.localizedMessage}")
                _snackbarMessage.value = "Error updating favorite status"
            }
        }
    }


    fun checkIfFavourite(property: DataItem) {
        viewModelScope.launch {
            try {
                val isFavourite = localDataBaseUseCase.isPropertyFavourite(property.id!!)
                _isFavourite.value = isFavourite
            } catch (ex: Exception) {
                Log.d("TAG", "checkIfFavourite: ${ex.localizedMessage}")
                _isFavourite.value = false
            }
        }
    }

    fun getAllProperties() {
        viewModelScope.launch {
            try {
                val properties = localDataBaseUseCase.getAllProperty()
                _propertyList.value = properties
                properties.forEach { property ->
                    Log.d("TAG", "Property: $property")
                }
            } catch (ex: Exception) {
                Log.d("TAG", "getAllProperties: ${ex.localizedMessage}")
            }
        }
    }

    fun resetFavouriteStatus() {
        _isFavourite.value = false
    }

    fun resetSnackbarMessage() {
        _snackbarMessage.value = null
    }
}