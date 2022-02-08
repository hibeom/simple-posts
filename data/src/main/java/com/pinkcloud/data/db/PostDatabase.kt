package com.pinkcloud.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pinkcloud.data.model.PostEntity

@Database(entities = [PostEntity::class, PostRemoteKey::class], version = 1, exportSchema = false)
abstract class PostDatabase: RoomDatabase() {

    abstract val postDao: PostDao
    abstract val postRemoteKeyDao: PostRemoteKeyDao
}