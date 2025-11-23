package com.example.assignmenttrack.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module //Module untuk membuat database, memberikan Database dan Dao untuk Repository
@InstallIn(SingletonComponent::class) //Memberi tahu bahwa module ini berjalan selama aplikasi nyala
object ModuleDatabase {
    @Provides // Memberitahu cara memberikan Databasenya
    @Singleton // Hanya ada satu instance
    fun provideDatabase(@ApplicationContext app: Context): TodoDatabase { //Memberikan database dengan
                                                                          // konteks aplikasi(Menyala selama
                                                                          // Aplikasi nyala)
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            TodoDatabase.NAME
        ).fallbackToDestructiveMigration(true).build()
    }


    @Provides // Kayak tadi tapi dao Task
    fun provideTaskDao(db: TodoDatabase): TaskDao = db.getTaskDao()

    @Provides // dao User
    fun provideUserDao(db: TodoDatabase): UserDao = db.getUserDao()
}
