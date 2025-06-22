package com.example.charitybox

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.charitybox.data.DonationDao
import com.example.charitybox.data.DonationDatabase
import com.example.charitybox.data.DonationEntity
import com.example.charitybox.donation.DonationType
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class DonationDaoTest {

    private lateinit var database: DonationDatabase
    private lateinit var donationDao: DonationDao

    @Before
    fun setUp() {
        // Initialize the in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DonationDatabase::class.java
        ).allowMainThreadQueries().build()

        donationDao = database.donationDao()
    }

    @After
    fun tearDown() {
        // Close the database after tests
        database.close()
    }

    @Test
    fun insertDonation_successfullyInsertsDonation() = runBlocking {
        val donation = DonationEntity(
            id = 1,
            amount = 100.0,
            donorName = "John Doe",
            donationType = DonationType.EDUCATION,
            scholarshipType = "Merit-Based",
            scholarshipRegion = "North",
            additionalSupport = "Books",
            contactDetails = "john@example.com",
            healthSupportType = "",
            preferredBeneficiaries = "",
            receiveImpactUpdates = true,
            specificHospitalOrOrg = "",
            disasterType = "",
            targetArea = "",
            aidType = "",
            donationDate = Date(),
            bloodGroup = "O+",
            povertySupportType = "1",
            disasterImpactArea = "test",
            womenEmpowermentFocus = "test",
            childProtectionFocus = "test",
            environmentalCause = "test",
            animalWelfareType = "test",
            disabilitySupportType = "test"
        )

        donationDao.insert(donation)

        val result = donationDao.getDonationById(1)
        assertNotNull(result)
        assertEquals(donation.amount, result?.amount)
        assertEquals(donation.donorName, result?.donorName)
    }

    @Test
    fun updateDonation_successfullyUpdatesDonation() = runBlocking {
        val donation = DonationEntity(
            id = 1,
            amount = 100.0,
            donorName = "John Doe",
            donationType = DonationType.EDUCATION,
            scholarshipType = "Merit-Based",
            scholarshipRegion = "North",
            additionalSupport = "Books",
            contactDetails = "john@example.com",
            healthSupportType = "",
            preferredBeneficiaries = "",
            receiveImpactUpdates = true,
            specificHospitalOrOrg = "",
            disasterType = "",
            targetArea = "",
            aidType = "",
            donationDate = Date(),
            bloodGroup = "O+",
            povertySupportType = "1",
            disasterImpactArea = "test",
            womenEmpowermentFocus = "test",
            childProtectionFocus = "test",
            environmentalCause = "test",
            animalWelfareType = "test",
            disabilitySupportType = "test"
        )

        donationDao.insert(donation)

        val updatedDonation = donation.copy(amount = 200.0, donorName = "Jane Doe")
        donationDao.updateDonation(updatedDonation)

        val result = donationDao.getDonationById(1)
        assertNotNull(result)
        assertEquals("200", result?.amount)
        assertEquals("Jane Doe", result?.donorName)
    }

//    @Test
//    fun deleteDonationById_successfullyDeletesDonation() = runBlocking {
//
//        )
//
//        donationDao.insert(donation)
//        donationDao.deleteDonationById(1) // Calls the @Query-based method
//
//        val result = donationDao.getDonationById(1)
//        assertNull(result) // Ensures the donation was deleted
//    }


    @Test
    fun getAllDonations_returnsAllInsertedDonations() = runBlocking {
        val donations = listOf(
            DonationEntity(
                id = 1,
                amount = 100.0,
                donorName = "John Doe",
                donationType = DonationType.EDUCATION,
                scholarshipType = "Merit-Based",
                scholarshipRegion = "North",
                additionalSupport = "Books",
                contactDetails = "john@example.com",
                healthSupportType = "",
                preferredBeneficiaries = "",
                receiveImpactUpdates = true,
                specificHospitalOrOrg = "",
                disasterType = "",
                targetArea = "",
                aidType = "",
                donationDate = Date(),
                bloodGroup = "O+",
                povertySupportType = "1",
                disasterImpactArea = "test",
                womenEmpowermentFocus = "test",
                childProtectionFocus = "test",
                environmentalCause = "test",
                animalWelfareType = "test",
                disabilitySupportType = "test"
            ),
//            Donation(
//                id = 1,
//                amount = 100.0,
//                donorName = "John Doe",
//                donationType = DonationType.EDUCATION,
//                scholarshipType = "Merit-Based",
//                scholarshipRegion = "North",
//                additionalSupport = "Books",
//                contactDetails = "john@example.com",
//                healthSupportType = "",
//                preferredBeneficiaries = "",
//                receiveImpactUpdates = true,
//                specificHospitalOrOrg = "",
//                disasterType = "",
//                targetArea = "",
//                aidType = "",
//                donationDate = Date()
//            )
        )

        donationDao.insertAll(donations)

        val result = donationDao.getAllDonations()
        assertEquals(1, result.size)
        assertEquals(donations[0].donorName, result[0].donorName)
//        assertEquals(donations[1].donorName, result[1].donorName)
    }
}
