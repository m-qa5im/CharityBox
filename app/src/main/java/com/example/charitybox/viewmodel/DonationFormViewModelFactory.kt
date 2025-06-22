package com.example.charitybox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.charitybox.repository.DonationRepositoryImpl

class DonationFormViewModelFactory(
    private val donationRepository: DonationRepositoryImpl
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DonationFormViewModel::class.java)) {
            // Provide the SavedStateHandle as well
            return DonationFormViewModel(donationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
