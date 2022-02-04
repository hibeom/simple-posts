package com.pinkcloud.shared.remote

import com.pinkcloud.shared.model.Post
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostResponse(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

fun PostResponse.asDomainModel(): Post {
    return Post(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}
