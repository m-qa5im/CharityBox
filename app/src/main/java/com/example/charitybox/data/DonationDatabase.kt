package com.example.charitybox.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DonationEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DonationDatabase : RoomDatabase() {

    abstract fun donationDao(): DonationDao

    companion object {
        @Volatile
        private var INSTANCE: DonationDatabase? = null

        fun getDatabase(context: Context): DonationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DonationDatabase::class.java,
                    "donation_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
