package com.pinkcloud.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class PostRemoteKey(
    @PrimaryKey
    @ColumnInfo(name = "post_id")
    val postId: Int,
    val nextKey: Int?
)
