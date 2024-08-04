package com.example.marketingapp.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.datamodel.getallproperty.DataItem
import com.example.domain.usecases.filterproperty.GetFilterPropertyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterPropertyViewModel @Inject constructor(
    private val getFilterPropertyUseCase: GetFilterPropertyUseCase
) : ViewModel() {

    private val _filteredProperties = MutableStateFlow<List<DataItem?>>(emptyList())
    val filteredProperties: StateFlow<List<DataItem?>> get() = _filteredProperties

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading


    fun getFilteredProperties(token: String, title: String, type: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val formattedToken = "Bearer $token"
            try {
                val response =
                    getFilterPropertyUseCase.getFilterProperty(formattedToken, title, type)
                _filteredProperties.value = response.data ?: emptyList()
            } catch (e: Exception) {
                _filteredProperties.value = emptyList()
                Log.e("tag", "Error fetching filtered properties: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}