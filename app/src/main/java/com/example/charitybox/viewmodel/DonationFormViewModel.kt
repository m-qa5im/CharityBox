package com.example.charitybox.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charitybox.data.DonationEntity
import com.example.charitybox.donation.DonationType
import com.example.charitybox.repository.DonationRepository
import com.example.charitybox.ui.theme.uistates.DonationFormState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class DonationFormViewModel(private val donationRepository: DonationRepository) : ViewModel() {

    private val _donationFormState = MutableStateFlow(DonationFormState())
    val donationFormState: StateFlow<DonationFormState> = _donationFormState.asStateFlow()

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    fun handleSuccess() {
        _toastMessage.value = "Operation Successful"
    }

    // Common Fields
    fun onAmountChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(amount = value)
    }

    fun onDonorNameChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(donorName = value)
    }

    fun onDisasterImpactAreaChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(disasterImpactArea = value)
    }

    fun onDonationDateChanged(value: Date) {
        _donationFormState.value = _donationFormState.value.copy(donationDate = value)
    }

    fun onDonationTypeChanged(value: DonationType) {
        _donationFormState.value = _donationFormState.value.copy(donationType = value)
    }

    // Education-Specific Fields
    fun onScholarshipTypeChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(scholarshipType = value)
    }

    fun onScholarshipRegionChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(scholarshipRegion = value)
    }

    fun onAdditionalSupportChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(additionalSupport = value)
    }

    // Health Support-Specific Fields
    fun onHealthSupportTypeChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(healthSupportType = value)
    }

    fun onPreferredBeneficiariesChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(preferredBeneficiaries = value)
    }

    fun onReceiveImpactUpdatesChanged(value: Boolean) {
        _donationFormState.value = _donationFormState.value.copy(receiveImpactUpdates = value)
    }

    fun onSpecificHospitalOrOrgChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(specificHospitalOrOrg = value)
    }

    // Disaster Relief-Specific Fields
    fun onDisasterTypeChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(disasterType = value)
    }

    fun onTargetAreaChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(targetArea = value)
    }

    fun onAidTypeChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(aidType = value)
    }

    // Blood Donation-Specific Fields
    fun onBloodGroupChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(bloodGroup = value)
    }

    // Poverty Alleviation-Specific Fields
    fun onPovertySupportTypeChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(povertySupportType = value)
    }

    // Women Empowerment-Specific Fields
    fun onWomenEmpowermentFocusChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(womenEmpowermentFocus = value)
    }

    // Child Protection-Specific Fields
    fun onChildProtectionFocusChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(childProtectionFocus = value)
    }

    // Environmental Protection-Specific Fields
    fun onEnvironmentalCauseChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(environmentalCause = value)
    }

    // Animal Welfare-Specific Fields
    fun onAnimalWelfareTypeChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(animalWelfareType = value)
    }

    // Disabled Persons-Specific Fields
    fun onDisabilitySupportTypeChanged(value: String) {
        _donationFormState.value = _donationFormState.value.copy(disabilitySupportType = value)
    }

    fun submitDonationForm(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val donationEntity = mapFormStateToEntity(donationFormState.value)
                val result = donationRepository.insertDonation(donationEntity)
                if (result) {
                    handleSuccess()
                    onSuccess()
                } else {
                    onError("Failed to submit the donation form")
                }
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred")
            }
        }
    }

    private fun mapFormStateToEntity(formState: DonationFormState): DonationEntity {
        return DonationEntity(
            id = 0, // Assuming auto-generated ID
            amount = formState.amount.toDoubleOrNull() ?: 0.0,
            donorName = formState.donorName,
            donationDate = formState.donationDate,
            donationType = formState.donationType,
            scholarshipType = formState.scholarshipType,
            scholarshipRegion = formState.scholarshipRegion,
            additionalSupport = formState.additionalSupport,
            contactDetails = formState.contactDetails,
            healthSupportType = formState.healthSupportType,
            preferredBeneficiaries = formState.preferredBeneficiaries,
            receiveImpactUpdates = formState.receiveImpactUpdates,
            specificHospitalOrOrg = formState.specificHospitalOrOrg,
            disasterType = formState.disasterType,
            targetArea = formState.targetArea,
            aidType = formState.aidType,
            bloodGroup = formState.bloodGroup,
            povertySupportType = formState.povertySupportType,
            disasterImpactArea = formState.disasterImpactArea,
            womenEmpowermentFocus = formState.womenEmpowermentFocus,
            childProtectionFocus = formState.childProtectionFocus,
            environmentalCause = formState.environmentalCause,
            animalWelfareType = formState.animalWelfareType,
            disabilitySupportType = formState.disabilitySupportType
        )
    }
}
