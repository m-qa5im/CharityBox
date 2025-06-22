package com.example.charitybox

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.charitybox.data.UserDao
import com.example.charitybox.data.UserDatabase
import com.example.charitybox.data.UserEntity
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var userDao: UserDao
    private lateinit var userDatabase: UserDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        userDatabase = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        userDao = userDatabase.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        userDatabase.close()
    }

    private var user1 = UserEntity("user1@gmail.com", "user1", "123456")
    private var user2 = UserEntity("user2@gmail.com", "user2", "123456")

    private suspend fun addOneItemToDb() {
        userDao.insert(user1)
    }

    private suspend fun addTwoItemsToDb() {
        userDao.insert(user1)
        userDao.insert(user2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb()
        val allItems = userDao.getAllUsers().first()
        assertEquals(allItems[0], user1)
    }
}