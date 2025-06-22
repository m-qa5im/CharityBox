package com.example.charitybox.donation

import androidx.annotation.DrawableRes
import com.example.charitybox.R

data class DonationCategory(
    val id: Int,
    val title: String,
    val description: String,
    @DrawableRes val imageResourceId: Int
)
val donationCategories = listOf(
    DonationCategory(
        id = 1,
        title = "Education and Scholarships",
        description = "Donate to support educational initiatives and scholarships for underprivileged students.",
        imageResourceId = R.drawable.education // Replace with actual image resource
    ),
    DonationCategory(
        id = 2,
        title = "Health and Medical Care",
        description = "Contribute towards providing medical care, treatments, and healthcare services.",
        imageResourceId = R.drawable.health // Replace with actual image resource
    ),
    DonationCategory(
        id = 3,
        title = "Blood Donation ",
        description = "Help save lives by donating blood and medical supplies to hospitals and clinics.",
        imageResourceId = R.drawable.blood // Replace with actual image resource
    ),
    DonationCategory(
        id = 4,
        title = "Poverty Alleviation ",
        description = "Support the fight against poverty by contributing to social welfare programs.",
        imageResourceId = R.drawable.welfare // Replace with actual image resource
    ),
    DonationCategory(
        id = 5,
        title = "Disaster Relief",
        description = "Provide aid and relief to those affected by natural disasters or emergencies.",
        imageResourceId = R.drawable.disaster_relief // Replace with actual image resource
    ),
    DonationCategory(
        id = 6,
        title = "Women Empowerment ",
        description = "Support women through education, healthcare, job opportunities, and safety initiatives.",
        imageResourceId = R.drawable.women_empowerment // Replace with actual image resource
    ),
    DonationCategory(
        id = 7,
        title = "Child Protection",
        description = "Help protect and support children with basic needs and education.",
        imageResourceId = R.drawable.child_protection // Replace with actual image resource
    ),
    DonationCategory(
        id = 8,
        title = "Environmental Protection",
        description = "Donate towards environmental protection and conservation projects.",
        imageResourceId = R.drawable.envirment // Replace with actual image resource
    ),
    DonationCategory(
        id = 9,
        title = "Animal Welfare",
        description = "Contribute to the well-being of animals by supporting animal rights organizations.",
        imageResourceId = R.drawable.animal_welfare // Replace with actual image resource
    ),
    DonationCategory(
        id = 10,
        title = "Disabled Persons",
        description = "Assist disabled individuals with accessible services, healthcare, and employment opportunities.",
        imageResourceId = R.drawable.disabled_support // Replace with actual image resource
    ),


)