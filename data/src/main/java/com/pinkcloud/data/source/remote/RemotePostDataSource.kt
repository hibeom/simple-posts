package com.pinkcloud.data.source.remote

import com.pinkcloud.data.api.CommentResponse
import com.pinkcloud.data.db.entity.PostEntity
import com.pinkcloud.domain.utils.Result

interface RemotePostDataSource {

    suspend fun getPost(postId: Int): Result<PostEntity>

    suspend fun deletePost(postEntity: PostEntity): Result<Void>

    suspend fun updatePost(postEntity: PostEntity): Result<PostEntity>

    suspend fun getComments(postId: Int): Result<List<CommentResponse>>
}