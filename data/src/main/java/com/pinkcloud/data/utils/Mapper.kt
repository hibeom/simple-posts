package com.pinkcloud.data.utils

import com.pinkcloud.data.model.CommentEntity
import com.pinkcloud.data.model.PostEntity
import com.pinkcloud.data.model.asDomainModel
import com.pinkcloud.domain.model.Comment
import com.pinkcloud.domain.model.Post
import com.pinkcloud.domain.utils.Result

fun Result<List<CommentEntity>>.asDomainModel(): Result<List<Comment>> {
    return when (this) {
        is Result.Success -> {
            Result.Success(data?.map { entity ->
                entity.asDomainModel()
            })
        }
        else -> Result.Error(message)
    }
}

@JvmName("asDomainModelPostEntity")
fun Result<PostEntity>.asDomainModel(): Result<Post> {
    return when (this) {
        is Result.Success -> Result.Success(data?.asDomainModel())
        else -> Result.Error(message)
    }
}

fun Post.asEntity() = PostEntity(id, userId, title, body)