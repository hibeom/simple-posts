package com.pinkcloud.shared.remote

import com.pinkcloud.shared.model.Comment
import com.pinkcloud.shared.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {

    @GET("posts")
    suspend fun getPostsPage(
        @Query("_start") start: Int = 0,
        @Query("_limit") limit: Int = 10
    ): List<PostResponse>

    @GET("posts/{postId}")
    suspend fun getPost(
        @Path("postId") postId: Int
    ): Response<PostResponse>

    @GET("posts/{postId}/comments")
    suspend fun getComments(
        @Path("postId") postId: Int
    ): Response<List<Comment>>
}