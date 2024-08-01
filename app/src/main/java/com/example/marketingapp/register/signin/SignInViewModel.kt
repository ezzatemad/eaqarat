package com.example.marketingapp.register.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.datamodel.register.signin.RegisterResponse
import com.example.domain.datamodel.register.signin.User
import com.example.domain.usecases.register.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _registerResponse = MutableStateFlow<RegisterResponse?>(null)
    val registerResponse: StateFlow<RegisterResponse?> get() = _registerResponse

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> get() = _nameError

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> get() = _emailError

    private val _phoneError = MutableStateFlow<String?>(null)
    val phoneError: StateFlow<String?> get() = _phoneError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> get() = _passwordError

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError: StateFlow<String?> get() = _confirmPasswordError


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _registerError = MutableLiveData<String?>(null)
    val registerError: LiveData<String?> get() = _registerError

    fun signIn(user: User) {
        if (validateInput(user)) {
            viewModelScope.launch {
                _isLoading.value = true
                _registerError.value = null
                try {
                    val response = signInUseCase.signIn(user)
                    _isLoading.value = false
                    _registerResponse.value = response
                } catch (ex: Exception) {
                    _isLoading.value = false
                    _registerError.value = "Email or phone number already exists"
                }
            }
        }
    }


    private fun validateInput(user: User): Boolean {
        var isValid = true

        if (user.username.isEmpty()) {
            _nameError.value = "Name is required"
            isValid = false
        } else {
            _nameError.value = null
        }

        if (user.email.isEmpty()) {
            _emailError.value = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            _emailError.value = "Invalid email format"
            isValid = false
        } else {
            _emailError.value = null
        }

        if (user.phone.isEmpty()) {
            _phoneError.value = "Phone number is required"
            isValid = false
        } else if (!user.phone.matches(Regex("^(010|012|015|011)\\d{8}\$"))) {
            _phoneError.value =
                "Phone number must start with 010, 012, 015, or 011 and be followed by 8 digits"
            isValid = false
        } else {
            _phoneError.value = null
        }

//        val passwordValue = password.value
//        val confirmPasswordValue = confirmPassword.value

        if (user.password.isNullOrEmpty()) {
            _passwordError.value = "Password is required"
            isValid = false
        } else {
            _passwordError.value = null
        }

        return isValid
    }
}
