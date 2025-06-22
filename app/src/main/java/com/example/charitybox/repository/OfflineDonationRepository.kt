package com.example.charitybox.repository

import com.example.charitybox.data.DonationDao
import com.example.charitybox.data.DonationEntity
import com.example.charitybox.donation.DonationType

class OfflineDonationRepository(private val donationDao: DonationDao) : DonationRepository {

    override suspend fun insertDonation(donation: DonationEntity): Boolean {
        return try {
            donationDao.insert(donation) // Ensure this line interacts with the DAO
            true // Indicate success
        } catch (e: Exception) {
            false // Indicate failure
        }
    }


    override suspend fun insertMultipleDonations(donations: List<DonationEntity>): Boolean {
        donations.forEach { validateDonation(it) }
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
        validateDonation(donation)
        donationDao.updateDonation(donation)
    }

    override suspend fun deleteDonationById(id: Long) {
        donationDao.deleteDonationById(id)
    }

    /**
     * Validate donation based on type and fields
     */
    private fun validateDonation(donation: DonationEntity) {
        when (donation.donationType) {
            DonationType.BLOOD_DONATION -> require(donation.bloodGroup.isNotEmpty()) {
                "Blood Group is required for Blood Donation."
            }
            DonationType.POVERTY_ALL -> require(donation.povertySupportType.isNotEmpty()) {
                "Poverty Support Type is required for Poverty Alleviation."
            }
            DonationType.DISASTER_RELIEF -> require(donation.disasterImpactArea.isNotEmpty()) {
                "Disaster Impact Area is required for Disaster Relief."
            }
            DonationType.WOMEN_EMPOWERMENT -> require(donation.womenEmpowermentFocus.isNotEmpty()) {
                "Women Empowerment Focus is required for Women Empowerment."
            }
            DonationType.CHILD_PROTECTION -> require(donation.childProtectionFocus.isNotEmpty()) {
                "Child Protection Focus is required for Child Protection."
            }
            DonationType.ENVIRONMENTAL -> require(donation.environmentalCause.isNotEmpty()) {
                "Environmental Cause is required for Environmental Protection."
            }
            DonationType.ANIMAL_WELFARE -> require(donation.animalWelfareType.isNotEmpty()) {
                "Animal Welfare Type is required for Animal Welfare."
            }
            DonationType.DISABILITY_SUPPORT -> require(donation.disabilitySupportType.isNotEmpty()) {
                "Disability Support Type is required for Disabled Persons."
            }
            else -> {
                // Default validation for generic donations
            }
        }
    }
}
