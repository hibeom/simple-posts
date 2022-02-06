package com.pinkcloud.shared.remote

import com.pinkcloud.shared.model.Comment
import retrofit2.Response
import retrofit2.http.*

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

    @DELETE("posts/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Int
    ): Response<Void>

    @Headers("Content-Type: application/json; charset=utf-8")
    @PATCH("posts/{postId}")
    suspend fun updatePost(
        @Path("postId") postId: Int,
        @Body body: Map<String, String>
    ): Response<PostResponse>
}