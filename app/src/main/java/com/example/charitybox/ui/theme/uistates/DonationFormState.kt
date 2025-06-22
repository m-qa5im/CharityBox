package com.example.charitybox.ui.theme.uistates

import com.example.charitybox.donation.DonationType
import java.util.Date

data class DonationFormState(
    val amount: String = "",
    val donorName: String = "",
    val donationDate: Date = Date(),
    val donationType: DonationType = DonationType.EDUCATION,
    val scholarshipType: String = "",
    val scholarshipRegion: String = "",
    val additionalSupport: String = "",
    val contactDetails: String = "",
    val healthSupportType: String = "",
    val preferredBeneficiaries: String = "",
    val receiveImpactUpdates: Boolean = false,
    val specificHospitalOrOrg: String = "",
    val disasterType: String = "",
    val targetArea: String = "",
    val aidType: String = "",
    val bloodGroup: String = "",                   // For Blood Donation
    val povertySupportType: String = "",          // For Poverty Alleviation
    val disasterImpactArea: String = "",          // For Disaster Relief
    val womenEmpowermentFocus: String = "",       // For Women Empowerment
    val childProtectionFocus: String = "",        // For Child Protection
    val environmentalCause: String = "",          // For Environmental Protection
    val animalWelfareType: String = "",           // For Animal Welfare
    val disabilitySupportType: String = ""        // For Disabled Persons
)

