package com.pinkcloud.data.model

import com.pinkcloud.domain.model.Comment
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentEntity(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)

fun CommentEntity.asDomainModel() = Comment(
    id = id,
    postId = postId,
    name = name,
    email = email,
    body = body
)