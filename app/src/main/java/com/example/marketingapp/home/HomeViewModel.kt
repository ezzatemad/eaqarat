package com.example.marketingapp.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.datamodel.getallproperty.PropertyResponse
import com.example.domain.usecases.getallproperty.GetAllPropertyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPropertyUseCase: GetAllPropertyUseCase
) : ViewModel() {

    private val _getAllPropertyResponse = MutableStateFlow<PropertyResponse?>(null)
    val getAllPropertyResponse: StateFlow<PropertyResponse?> get() = _getAllPropertyResponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun getAllProperty(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val formattedToken = "Bearer $token"
            Log.d("HomeViewModel", "Starting network request with token: $formattedToken")
            try {
                val response = getAllPropertyUseCase.getAllProperty(formattedToken)
                _getAllPropertyResponse.value = response
                Log.d("HomeViewModel", "Network request successful, response: $response")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Network request failed: ${e.message}")
            } finally {
                _isLoading.value = false
                Log.d("HomeViewModel", "Network request finished")
            }
        }
    }
}
