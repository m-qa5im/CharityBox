package com.example.charitybox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charitybox.donation.donationCategories
import com.example.charitybox.ui.theme.uistates.HomeScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun checkIfLoggedIn(): Boolean {
        return true
    }

    fun fetchUserData() {
        val isLoggedIn = checkIfLoggedIn()
        _uiState.update { it.copy(isLoggedIn = isLoggedIn) }
    }

    fun toggleShowAdditionalContent(categoryId: Int) {
        _uiState.update { currentState ->
            val updatedVisibility = currentState.showAdditionalContent.toMutableMap().apply {
                this[categoryId] = !(this[categoryId] ?: false)
            }
            currentState.copy(showAdditionalContent = updatedVisibility)
        }
    }

    init {
        // Initialize with all categories displayed
        _uiState.update {
            it.copy(filteredCategories = donationCategories)
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update {
            val filtered = if (query.isEmpty()) {
                donationCategories // Show all categories when the search query is empty
            } else {
                donationCategories.filter {
                    it.title.contains(query, ignoreCase = true)
                }
            }
            it.copy(searchQuery = query, filteredCategories = filtered)
        }
    }
}
