package com.example.assignmenttrack.database

import com.example.assignmenttrack.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    fun getUser(): Flow<User?> = userDao.getUser()

    suspend fun updateUser(user: User) = userDao.updateUser(user)

    // Test dulu
    suspend fun insertDefaulUser(user: User){
        userDao.insertDefaultUser(user)
    }
}