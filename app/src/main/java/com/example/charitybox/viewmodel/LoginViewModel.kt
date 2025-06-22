package com.example.charitybox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charitybox.ui.theme.uistates.LoginUiState
import com.example.charitybox.repository.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class LoginViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Password visibility state
    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

    // Update email
    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    // Update password
    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    // Toggle password visibility
   /* fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }*/

    // Login logic
    fun login(email: String, password: String) {
        viewModelScope.launch {
            val loginState = usersRepository.getUserByEmail(email).first()

            if (loginState.isLoginSuccessful && loginState.password == password) {
                // Update login state for successful login
                _uiState.value = _uiState.value.copy(isLoginSuccessful = true, errorMessage = "")
            } else {
                // Update state with error message
                _uiState.value = _uiState.value.copy(isLoginSuccessful = false, errorMessage = "Invalid credentials")
            }
        }
    }



    private fun validateForm(email: String, password: String): Boolean {
        return when {
            email.isBlank() -> {
                _uiState.value = _uiState.value.copy(errorMessage = "Email cannot be empty")
                false
            }
            password.isBlank() -> {
                _uiState.value = _uiState.value.copy(errorMessage = "Password cannot be empty")
                false
            }
            password.length < 6 -> {
                _uiState.value = _uiState.value.copy(errorMessage = "Password must be at least 6 characters long")
                false
            }
            else -> true
        }
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }
}
