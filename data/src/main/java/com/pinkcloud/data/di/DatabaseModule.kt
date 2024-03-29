package com.pinkcloud.data.di

import android.content.Context
import androidx.room.Room
import com.pinkcloud.data.db.PostDatabase
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
    fun providePostDao(database: PostDatabase) = database.postDao

    @Provides
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