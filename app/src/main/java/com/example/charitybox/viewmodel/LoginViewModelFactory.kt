package com.example.charitybox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.charitybox.repository.UsersRepository

class LoginViewModelFactory(
    private val usersRepository: UsersRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(usersRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
