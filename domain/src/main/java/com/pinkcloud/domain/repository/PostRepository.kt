package com.pinkcloud.domain.repository

import androidx.paging.PagingData
import com.pinkcloud.domain.model.Comment
import com.pinkcloud.domain.model.Post
import com.pinkcloud.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getPostPagingFlow(): Flow<PagingData<Post>>

    suspend fun getPost(postId: Int): Result<Post>

    fun getPostFlow(postId: Int): Flow<Post?>

    suspend fun getComments(postId: Int): Result<List<Comment>>

    suspend fun deletePost(post: Post): Result<Void>

    suspend fun updatePost(post: Post): Result<Post>
}