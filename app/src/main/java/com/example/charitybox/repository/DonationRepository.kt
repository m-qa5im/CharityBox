package com.example.charitybox.repository

import com.example.charitybox.data.DonationDao
import com.example.charitybox.data.DonationEntity

interface DonationRepository {


    suspend fun insertDonation(donation: DonationEntity): Boolean
    suspend fun insertMultipleDonations(donations: List<DonationEntity>): Boolean
    suspend fun getAllDonations(): List<DonationEntity>
    suspend fun getDonationById(id: Long): DonationEntity?
    suspend fun updateDonation(donation: DonationEntity)
    suspend fun deleteDonationById(id: Long)

}
