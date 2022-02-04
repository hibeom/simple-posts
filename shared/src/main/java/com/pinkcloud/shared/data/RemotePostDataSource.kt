package com.pinkcloud.shared.data

import com.pinkcloud.shared.model.Comment
import com.pinkcloud.shared.model.Post
import com.pinkcloud.shared.remote.BaseApiResponse
import com.pinkcloud.shared.remote.PostService
import com.pinkcloud.shared.remote.Result
import com.pinkcloud.shared.remote.asDomainModel
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
        val result = safeApiCall { postService.getPost(postId) }
        return when (result) {
            is Result.Success -> Result.Success(result.data!!.asDomainModel())
            is Result.Error -> Result.Error(result.message!!, null)
            else -> Result.Loading()
        }
    }

    override suspend fun deletePost(postId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePost(post: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun getComments(postId: Int): Result<List<Comment>> {
        return safeApiCall { postService.getComments(postId) }
    }
}