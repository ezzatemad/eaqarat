package com.example.marketingapp.register.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.datamodel.register.login.LoginRequest
import com.example.domain.datamodel.register.login.LoginResponse
import com.example.domain.usecases.register.LoginUseCase
import com.example.marketingapp.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse: StateFlow<LoginResponse?> get() = _loginResponse

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> get() = _emailError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> get() = _passwordError

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _loginError = MutableLiveData<String?>(null)
    val loginError: LiveData<String?> get() = _loginError

    fun login(loginRequest: LoginRequest) {
        if (validateInput(loginRequest)) {
            viewModelScope.launch {
                _isLoading.value = true
                _loginError.value = null
                try {
                    val response = loginUseCase.login(loginRequest)
                    _loginResponse.value = response
                    Log.d("LoginViewModel", "Response: $response")
                    if (response.token == null) {
                        _loginError.value = response.message ?: "Invalid email or password"
                    }
                } catch (e: Exception) {
                    Log.e("LoginViewModel", "Error: ${e.message}")
                    _loginError.value = "An error occurred: ${e.message}"
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }


    private fun validateInput(loginRequest: LoginRequest): Boolean {
        var isValid = true

        if (loginRequest.email.isEmpty()) {
            _emailError.value = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(loginRequest.email).matches()) {
            _emailError.value = "Invalid email format"
            isValid = false
        } else {
            _emailError.value = null
        }
        if (loginRequest.password.isEmpty()) {
            _passwordError.value = "Password is required"
            isValid = false
        } else {
            _passwordError.value = null
        }
        return isValid
    }

    fun clearLoginError() {
        _loginError.value = null
    }
}
