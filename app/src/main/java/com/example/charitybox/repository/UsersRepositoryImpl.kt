package com.example.charitybox.repository

import com.example.charitybox.data.UserEntity
import com.example.charitybox.ui.theme.uistates.LoginUiState
import com.example.charitybox.data.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UsersRepositoryImpl(private val userDao: UserDao) : UsersRepository {

    override suspend fun getUserByEmail(email: String): Flow<LoginUiState> {
        return userDao.getUserByEmail(email).map { userEntity ->
            if (userEntity != null) {
                LoginUiState(
                    name = userEntity.name,
                    email = userEntity.email,
                    password = userEntity.password,
                    isLoginSuccessful = true
                )
            } else {
                LoginUiState(
                    errorMessage = "User not found",
                    isLoginSuccessful = false
                )
            }
        }
    }

    override suspend fun insertUser(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

    override suspend fun updateUser(user: UserEntity) {
        userDao.update(user.name, user.password, user.email)
    }

    override suspend fun deleteUserByEmail(email: String) {
        userDao.deleteUserByEmail(email)
    }
}
