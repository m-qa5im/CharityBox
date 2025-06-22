package com.example.charitybox.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charitybox.data.UserEntity
import com.example.charitybox.repository.UsersRepository
import com.example.charitybox.ui.theme.uistates.SignupUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SignUpViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState

    // StateFlow for navigation to login screen after successful sign-up
    private val _navigateToLogin = MutableStateFlow(false)
    val navigateToLogin: StateFlow<Boolean> = _navigateToLogin

    fun onNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
    }

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = newConfirmPassword)
    }

    fun signUp(name: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            // Validate form
            val isValid = validateForm(SignupUiState(name = name, email = email, password = password, confirmPassword = confirmPassword))

            if (!isValid) return@launch

            // Check if the user already exists
            val existingUser = usersRepository.getUserByEmail(email).first()

            if (existingUser.isLoginSuccessful) {
                _uiState.value = SignupUiState(errorMessage = "Email already exists")
            } else {
                val newUser = UserEntity(name = name, email = email, password = password)
                usersRepository.insertUser(newUser)

                // Success: Set the UI state and trigger navigation
                _uiState.value = SignupUiState(isSignUpSuccessful = true)

                _navigateToLogin.value = true // Trigger navigation to login
            }
        }
    }

    private fun validateForm(state: SignupUiState): Boolean {
        return when {
            state.name.isBlank() -> {
                _uiState.value = _uiState.value.copy(errorMessage = "Name cannot be empty")
                false
            }
            state.email.isBlank() -> {
                _uiState.value = _uiState.value.copy(errorMessage = "Email cannot be empty")
                false
            }
            state.password.isBlank() -> {
                _uiState.value = _uiState.value.copy(errorMessage = "Password cannot be empty")
                false
            }
            state.confirmPassword.isBlank() -> {
                _uiState.value = _uiState.value.copy(errorMessage = "Confirm Password cannot be empty")
                false
            }
            state.password != state.confirmPassword -> {
                _uiState.value = _uiState.value.copy(errorMessage = "Passwords do not match")
                false
            }
            state.password.length < 6 -> {
                _uiState.value = _uiState.value.copy(errorMessage = "Password must be at least 6 characters long")
                false
            }
            else -> true
        }
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }
}
