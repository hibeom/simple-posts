package com.pinkcloud.shared.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pinkcloud.shared.model.Post

@Database(entities = [Post::class, PostRemoteKey::class], version = 1, exportSchema = false)
abstract class PostDatabase: RoomDatabase() {

    abstract val postDao: PostDao
    abstract val postRemoteKeyDao: PostRemoteKeyDao
}