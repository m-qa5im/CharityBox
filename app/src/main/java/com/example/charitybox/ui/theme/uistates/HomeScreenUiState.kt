package com.example.charitybox.ui.theme.uistates

import com.example.charitybox.donation.DonationCategory

data class HomeScreenUiState(
    val isLoggedIn: Boolean = false,
    val searchQuery: String = "",
    val filteredCategories: List<DonationCategory> = emptyList(),
    val showAdditionalContent: Map<Int, Boolean> = emptyMap()
)
