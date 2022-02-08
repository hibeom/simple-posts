package com.pinkcloud.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pinkcloud.domain.model.Post

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    val title: String,
    val body: String
)

fun PostEntity.asDomainModel() = Post(
    id = id,
    userId = userId,
    title = title,
    body = body
)