package com.pinkcloud.data.source.local

import com.pinkcloud.data.db.entity.PostEntity
import com.pinkcloud.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface LocalPostDataSource {

    suspend fun getPost(postId: Int): Result<PostEntity>

    fun getPostFlow(postId: Int): Flow<PostEntity?>

    suspend fun deletePost(postEntity: PostEntity): Result<Void>

    suspend fun updatePost(postEntity: PostEntity): Result<PostEntity>
}