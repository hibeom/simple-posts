package com.pinkcloud.data.utils

import com.pinkcloud.data.api.CommentResponse
import com.pinkcloud.data.db.entity.PostEntity
import com.pinkcloud.data.db.entity.asDomainModel
import com.pinkcloud.data.api.PostResponse
import com.pinkcloud.data.api.asDomainModel
import com.pinkcloud.data.api.asEntity
import com.pinkcloud.domain.model.Comment
import com.pinkcloud.domain.model.Post
import com.pinkcloud.domain.utils.Result

fun Result<List<CommentResponse>>.asDomainModel(): Result<List<Comment>> {
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

@JvmName("asDomainModelPostResponse")
fun Result<PostResponse>.asEntity(): Result<PostEntity> {
    return when (this) {
        is Result.Success -> Result.Success(data?.asEntity())
        is Result.Error -> Result.Error(message!!, null)
        else -> Result.Loading()
    }
}