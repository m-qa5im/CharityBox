package com.example.charitybox.ui.theme.uistates

data class LoginUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val errorMessage: String = "",
    val isLoginSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    var isPasswordVisible: Boolean = false
)
