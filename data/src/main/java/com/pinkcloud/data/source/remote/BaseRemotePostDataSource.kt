package com.pinkcloud.data.source.remote

import com.pinkcloud.data.model.CommentEntity
import com.pinkcloud.data.model.PostEntity
import com.pinkcloud.data.remote.BaseApiResponse
import com.pinkcloud.data.remote.PostResponse
import com.pinkcloud.data.remote.PostService
import com.pinkcloud.data.remote.asEntity
import com.pinkcloud.domain.utils.Result
import javax.inject.Inject

class BaseRemotePostDataSource @Inject constructor(
    private val postService: PostService
) : RemotePostDataSource, BaseApiResponse() {

    override suspend fun getPost(postId: Int): Result<PostEntity> {
        return safeApiCall { postService.getPost(postId) }.asDomainModel()
    }

    override suspend fun deletePost(postEntity: PostEntity): Result<Void> {
        return safeApiCall { postService.deletePost(postEntity.id) }
    }

    override suspend fun updatePost(postEntity: PostEntity): Result<PostEntity> {
        return safeApiCall { postService.updatePost(
            postId = postEntity.id,
            body = hashMapOf("title" to postEntity.title,  "body" to postEntity.body)
        ) }.asDomainModel()
    }

    override suspend fun getComments(postId: Int): Result<List<CommentEntity>> {
        return safeApiCall { postService.getComments(postId) }
    }

    private fun Result<PostResponse>.asDomainModel(): Result<PostEntity> {
        return when (this) {
            is Result.Success -> Result.Success(data?.asEntity())
            is Result.Error -> Result.Error(message!!, null)
            else -> Result.Loading()
        }
    }
}