package com.pinkcloud.data.api

import com.pinkcloud.data.db.entity.PostEntity
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
