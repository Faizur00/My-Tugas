package com.example.assignmenttrack.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module //Baca Module Database
@InstallIn(SingletonComponent::class)
object ModuleRepository {
    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository{
        return TaskRepository(taskDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository{
        return UserRepository(userDao)
    }
}