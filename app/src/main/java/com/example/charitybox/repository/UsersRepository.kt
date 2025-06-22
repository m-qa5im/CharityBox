package com.example.charitybox.repository

import com.example.charitybox.data.UserEntity
import com.example.charitybox.ui.theme.uistates.LoginUiState
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getUserByEmail(email: String): Flow<LoginUiState> // Return LoginUiState

    suspend fun insertUser(userEntity: UserEntity)

    suspend fun updateUser(user: UserEntity)

    suspend fun deleteUserByEmail(email: String)
}
