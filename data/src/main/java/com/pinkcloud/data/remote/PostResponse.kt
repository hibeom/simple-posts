package com.pinkcloud.data.remote

import com.pinkcloud.data.model.PostEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostResponse(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

fun PostResponse.asEntity(): PostEntity {
    return PostEntity(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}
