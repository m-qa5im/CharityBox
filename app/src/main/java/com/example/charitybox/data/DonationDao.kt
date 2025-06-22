package com.example.charitybox.data

import androidx.room.*

@Dao
interface DonationDao {

    @Insert
    suspend fun insert(donation: DonationEntity)

    @Insert
    suspend fun insertAll(donations: List<DonationEntity>)

    @Query("SELECT * FROM donations")
    suspend fun getAllDonations(): List<DonationEntity>

    @Query("SELECT * FROM donations WHERE id = :id")
    suspend fun getDonationById(id: Long): DonationEntity?

    @Update
    suspend fun updateDonation(donation: DonationEntity)

    @Delete
    suspend fun deleteDonationById(donation: DonationEntity)

    @Query("DELETE FROM donations WHERE id = :id")
    suspend fun deleteDonationById(id: Long)


}
