package com.example.charitybox.ui.theme.uistates

data class SignupUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val errorMessage: String = "",
    val passwordError: String ="",
    val isLoading: Boolean = false,
    val isSignUpSuccessful: Boolean = false
)

