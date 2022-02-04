package com.pinkcloud.shared.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostResponse(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)
