package com.example.charitybox.repository

import com.example.charitybox.data.DonationDao
import com.example.charitybox.data.DonationEntity
import com.example.charitybox.ui.theme.uistates.DonationFormState

class DonationRepositoryImpl(private val donationDao: DonationDao) : DonationRepository {

    override suspend fun insertDonation(donation: DonationEntity): Boolean {
        return try {
            donationDao.insert(donation) // Ensure this line interacts with the DAO
            true // Indicate success
        } catch (e: Exception) {
            false // Indicate failure
        }
    }


    override suspend fun insertMultipleDonations(donations: List<DonationEntity>): Boolean {
        donationDao.insertAll(donations)
        return true
    }

    override suspend fun getAllDonations(): List<DonationEntity> {
        return donationDao.getAllDonations()
    }

    override suspend fun getDonationById(id: Long): DonationEntity? {
        return donationDao.getDonationById(id)
    }

    override suspend fun updateDonation(donation: DonationEntity) {
        donationDao.updateDonation(donation)
    }

    override suspend fun deleteDonationById(id: Long) {
        donationDao.deleteDonationById(id)
    }

    /**
     * Insert a donation from DonationFormState.
     */
    suspend fun insertDonationFromForm(formState: DonationFormState): Boolean {
        val donationEntity = mapFormStateToEntity(formState)
        return insertDonation(donationEntity)
    }

    /**
     * Map DonationFormState to DonationEntity.
     */
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
