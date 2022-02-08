package com.pinkcloud.data.source

import com.pinkcloud.data.model.CommentEntity
import com.pinkcloud.data.model.PostEntity
import com.pinkcloud.data.remote.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.pinkcloud.domain.utils.Result

class RemotePostDataSource @Inject constructor(
    private val postService: PostService
) : PostDataSource, BaseApiResponse() {

    override suspend fun getPost(postId: Int): Result<PostEntity> {
        return safeApiCall { postService.getPost(postId) }.asDomainModel()
    }

    override fun getPostFlow(postId: Int): Flow<PostEntity> {
        TODO("Not yet implemented")
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
            is Result.Success -> Result.Success(data?.asDomainModel())
            is Result.Error -> Result.Error(message!!, null)
            else -> Result.Loading()
        }
    }
}