package com.example.charitybox.viewmodel

import androidx.lifecycle.ViewModel
import com.example.charitybox.CharityBoxScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationViewModel : ViewModel() {

    // StateFlow to hold the current destination
    private val _currentDestination = MutableStateFlow(CharityBoxScreen.Splash)
    val currentDestination: StateFlow<CharityBoxScreen> = _currentDestination.asStateFlow()

    // Method to update the current destination
    fun updateCurrentDestination(destination: CharityBoxScreen) {
        _currentDestination.value = destination
    }

    // StateFlow that holds whether the user is logged in
    private val _isUserLoggedIn = MutableStateFlow(false)  // Initially assuming user is not logged in
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    // Method to set the user logged-in state
    fun setUserLoggedIn(isLoggedIn: Boolean) {
        _isUserLoggedIn.value = isLoggedIn
    }

    // Helper function to navigate based on login status
    fun determineInitialDestination(): String {
        return if (_isUserLoggedIn.value) "home" else "login"
    }
}
