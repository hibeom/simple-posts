package com.pinkcloud.shared.di

import android.content.Context
import androidx.room.Room
import com.pinkcloud.shared.db.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePostDao(database: PostDatabase) = database.postDao

    @Provides
    @Singleton
    fun providePostRemoteKeyDao(database: PostDatabase) = database.postRemoteKeyDao

    @Provides
    @Singleton
    fun providePostDatabase(@ApplicationContext context: Context): PostDatabase {
        return Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            "post_database"
        ).build()
    }
}