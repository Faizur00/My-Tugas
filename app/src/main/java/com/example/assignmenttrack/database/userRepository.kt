package com.example.assignmenttrack.database

import com.example.assignmenttrack.model.User
import com.example.assignmenttrack.uiStateData.defaultUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class UserRepository(private val userDao: UserDao, private val taskDao: TaskDao) {
    // Flow untuk user
    fun getUser(): Flow<User?> = userDao.getUser()

    // Flow untuk stat
    fun getStat() = taskDao.getStat()

    suspend fun deleteAllTasks() = taskDao.deleteAllTasks()
    suspend fun updateName(name: String) = userDao.updateName(name)
    suspend fun updatePhotoProfile(path: String) = userDao.updatePhotoProfile(path)

    suspend fun initUser() {
        if (userDao.getUser().firstOrNull() == null) {
            userDao.insertDefaultUser(defaultUser)
        }
    }
}