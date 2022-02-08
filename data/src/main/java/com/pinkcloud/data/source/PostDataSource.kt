package com.pinkcloud.data.source

import com.pinkcloud.data.model.CommentEntity
import com.pinkcloud.data.model.PostEntity
import com.pinkcloud.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface PostDataSource {

    suspend fun getPost(postId: Int): Result<PostEntity>

    fun getPostFlow(postId: Int): Flow<PostEntity>

    suspend fun deletePost(postEntity: PostEntity): Result<Void>

    suspend fun updatePost(postEntity: PostEntity): Result<PostEntity>

    suspend fun getComments(postId: Int): Result<List<CommentEntity>>
}