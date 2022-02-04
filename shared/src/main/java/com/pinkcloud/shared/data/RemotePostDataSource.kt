package com.pinkcloud.shared.data

import com.pinkcloud.shared.model.Comment
import com.pinkcloud.shared.model.Post
import com.pinkcloud.shared.remote.PostService
import javax.inject.Inject

class RemotePostDataSource @Inject constructor(
    private val postService: PostService
) : PostDataSource {
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

    override suspend fun getPost(postId: Int): Post {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(postId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePost(post: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun getComments(postId: Int): List<Comment> {
        TODO("Not yet implemented")
    }
}