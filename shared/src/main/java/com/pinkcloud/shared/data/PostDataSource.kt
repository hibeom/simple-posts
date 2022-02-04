package com.pinkcloud.shared.data

import com.pinkcloud.shared.model.Comment
import com.pinkcloud.shared.model.Post
import com.pinkcloud.shared.remote.Result

interface PostDataSource {

    suspend fun getPosts(start: Int, limit: Int): List<Post>

    suspend fun getPost(postId: Int): Result<Post>

    suspend fun deletePost(postId: Int)

    suspend fun updatePost(post: Post)

    suspend fun getComments(postId: Int): Result<List<Comment>>
}