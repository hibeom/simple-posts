package com.pinkcloud.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comment(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)
