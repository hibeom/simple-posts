package com.pinkcloud.shared.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PostRemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE post_id = :postId")
    suspend fun remoteKeyPostId(postId: Int): PostRemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKey()
}