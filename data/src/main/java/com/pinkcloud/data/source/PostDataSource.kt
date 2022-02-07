package com.pinkcloud.data.source

import com.pinkcloud.data.model.Comment
import com.pinkcloud.data.model.Post
import com.pinkcloud.data.remote.Result
import kotlinx.coroutines.flow.Flow

interface PostDataSource {

    suspend fun getPosts(start: Int, limit: Int): List<Post>

    suspend fun getPost(postId: Int): Result<Post>

    fun getPostFlow(postId: Int): Flow<Post>

    suspend fun deletePost(post: Post): Result<Void>

    suspend fun updatePost(post: Post): Result<Post>

    suspend fun getComments(postId: Int): Result<List<Comment>>
}