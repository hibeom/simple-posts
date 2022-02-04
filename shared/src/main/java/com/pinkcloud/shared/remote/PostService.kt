package com.pinkcloud.shared.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {

    @GET("posts")
    suspend fun getPostsPage(
        @Query("_start") start: Int = 0,
        @Query("_limit") limit: Int = 10
    ): List<PostResponse>
}