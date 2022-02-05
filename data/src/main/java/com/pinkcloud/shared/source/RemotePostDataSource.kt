package com.pinkcloud.shared.source

import com.pinkcloud.shared.model.Comment
import com.pinkcloud.shared.model.Post
import com.pinkcloud.shared.remote.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemotePostDataSource @Inject constructor(
    private val postService: PostService
) : PostDataSource, BaseApiResponse() {
    override suspend fun getPosts(start: Int, limit: Int): List<Post> {
        return postService.getPostsPage(start, limit).map {
            Post(
                id = it.id,
                userId = it.userId,
                title = it.title,
                body = it.body
            )
        }
    }

    override suspend fun getPost(postId: Int): Result<Post> {
        return safeApiCall { postService.getPost(postId) }.asDomainModel()
    }

    override fun getPostFlow(postId: Int): Flow<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(post: Post): Result<Void> {
        return safeApiCall { postService.deletePost(post.id) }
    }

    override suspend fun updatePost(post: Post): Result<Post> {
        return safeApiCall { postService.updatePost(
            postId = post.id,
            body = hashMapOf("title" to post.title,  "body" to post.body)
        ) }.asDomainModel()
    }

    override suspend fun getComments(postId: Int): Result<List<Comment>> {
        return safeApiCall { postService.getComments(postId) }
    }

    private fun Result<PostResponse>.asDomainModel(): Result<Post> {
        return when (this) {
            is Result.Success -> Result.Success(data?.asDomainModel())
            is Result.Error -> Result.Error(message!!, null)
            else -> Result.Loading()
        }
    }
}