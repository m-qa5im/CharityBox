package com.example.charitybox.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun getUserByEmail(email: String): Flow<UserEntity?>

    @Query("UPDATE users SET name = :name, password = :password WHERE email = :email")
    suspend fun update(name: String, password: String, email: String)

    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUserByEmail(email: String)

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>
}
