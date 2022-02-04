package com.pinkcloud.shared.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    val title: String,
    val body: String
)
