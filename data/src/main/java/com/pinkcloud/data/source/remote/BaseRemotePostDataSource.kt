package com.pinkcloud.data.source.remote

import com.pinkcloud.data.db.entity.PostEntity
import com.pinkcloud.data.api.BaseApiResponse
import com.pinkcloud.data.api.CommentResponse
import com.pinkcloud.data.api.PostService
import com.pinkcloud.data.utils.asEntity
import com.pinkcloud.domain.utils.Result
import javax.inject.Inject

class BaseRemotePostDataSource @Inject constructor(
    private val postService: PostService
) : RemotePostDataSource, BaseApiResponse() {

    override suspend fun getPost(postId: Int): Result<PostEntity> {
        return safeApiCall { postService.getPost(postId) }.asEntity()
    }

    override suspend fun deletePost(postEntity: PostEntity): Result<Void> {
        return safeApiCall { postService.deletePost(postEntity.id) }
    }

    override suspend fun updatePost(postEntity: PostEntity): Result<PostEntity> {
        return safeApiCall { postService.updatePost(
            postId = postEntity.id,
            body = hashMapOf("title" to postEntity.title,  "body" to postEntity.body)
        ) }.asEntity()
    }

    override suspend fun getComments(postId: Int): Result<List<CommentResponse>> {
        return safeApiCall { postService.getComments(postId) }
    }
}