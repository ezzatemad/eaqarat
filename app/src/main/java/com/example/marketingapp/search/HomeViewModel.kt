package com.example.marketingapp.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.datamodel.getallproperty.DataItem
import com.example.domain.datamodel.getallproperty.PropertyResponse
import com.example.domain.datamodel.searchproperty.SearchResponse
import com.example.domain.usecases.getallproperty.GetAllPropertyUseCase
import com.example.domain.usecases.search.SearchUseCase
import com.example.domain.usecases.sortproperty.SortUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPropertyUseCase: GetAllPropertyUseCase,
    private val searchUseCase: SearchUseCase,
    private val sortUseCase: SortUseCase
) : ViewModel() {
    private val _allProperties = MutableStateFlow<List<DataItem?>>(emptyList())
    private val _filteredProperties = MutableStateFlow<List<DataItem?>>(emptyList())
    val filteredProperties: StateFlow<List<DataItem?>> get() = _filteredProperties

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> get() = _searchText.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _selectedStatus = MutableStateFlow("All")
    val selectedStatus: StateFlow<String> = _selectedStatus.asStateFlow()

    // Define default sort option as newest
    private val _selectedSortOption = MutableStateFlow(SortOption("listedAt", false))
    val selectedSortOption: StateFlow<SortOption> = _selectedSortOption.asStateFlow()

    init {
        // Initialize with default sort option
        _selectedSortOption.value = SortOption("listedAt", false)
    }

    data class SortOption(val sortBy: String, val ascending: Boolean)

    fun setSelectedSortOption(option: SortOption) {
        _selectedSortOption.value = option
        applySortingAndFiltering()
    }

    fun setSelectedStatus(status: String) {
        _selectedStatus.value = status
        applySortingAndFiltering()
    }

    fun updateSearchText(query: String) {
        _searchText.value = query
        applySortingAndFiltering()
    }

    private fun applySortingAndFiltering() {
        val status = _selectedStatus.value
        val query = _searchText.value

        val filteredByStatus = if (status == "All") {
            _allProperties.value
        } else {
            _allProperties.value.filter { it?.status == status }
        }

        val filteredBySearch = if (query.isEmpty()) {
            filteredByStatus
        } else {
            filteredByStatus.filter { it?.status?.contains(query, ignoreCase = true) == true }
        }

        val sortedProperties = when (_selectedSortOption.value.sortBy) {
            "listedAt" -> filteredBySearch.sortedWith(compareBy { it?.listedAt })
            "price" -> filteredBySearch.sortedWith(compareBy { it?.price })
            else -> filteredBySearch
        }.let {
            if (_selectedSortOption.value.ascending) it else it.reversed()
        }

        _filteredProperties.value = sortedProperties
    }

    fun searchProperties(token: String, query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val formattedToken = "Bearer $token"
            Log.d("HomeViewModel", "Starting search request with token: $formattedToken")
            try {
                val response: SearchResponse = searchUseCase.search(formattedToken, query)
                _allProperties.value = response.data ?: emptyList()
                applySortingAndFiltering()
                Log.d("HomeViewModel", "Search request successful, response: $response")
            } catch (e: Exception) {
                _allProperties.value = emptyList()
                _filteredProperties.value = emptyList()
                Log.e("HomeViewModel", "Search request failed: ${e.message}")
            } finally {
                _isLoading.value = false
                Log.d("HomeViewModel", "Search request finished")
            }
        }
    }

    fun getAllProperty(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val formattedToken = "Bearer $token"
            try {
                val response = getAllPropertyUseCase.getAllProperty(formattedToken)
                _allProperties.value = response.data ?: emptyList()
                applySortingAndFiltering()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Network request failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun sortPropertiesByNewest(properties: List<DataItem?>): List<DataItem?> {
        return properties.sortedByDescending { it?.listedAt }
    }

    fun filterPropertiesByStatus(status: String) {
        val filtered = if (status == "All") {
            _allProperties.value
        } else {
            _allProperties.value.filter { it?.status == status }
        }

        // Apply sorting by newest
        _filteredProperties.value = sortPropertiesByNewest(filtered)
    }
}
 