package com.pinkcloud.data.source

import com.pinkcloud.data.db.PostDao
import com.pinkcloud.data.db.PostRemoteKeyDao
import com.pinkcloud.data.model.Comment
import com.pinkcloud.data.model.Post
import com.pinkcloud.data.remote.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class LocalPostDataSource @Inject constructor(
    private val postDao: PostDao,
    private val postRemoteKeyDao: PostRemoteKeyDao
) : PostDataSource {
    override suspend fun getPosts(start: Int, limit: Int): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getPost(postId: Int): Result<Post> {
        return try {
            val post = postDao.getPost(postId)
            Result.Success(post)
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override fun getPostFlow(postId: Int): Flow<Post> {
        return postDao.getPostFlow(postId).distinctUntilChanged()
    }

    override suspend fun deletePost(post: Post): Result<Void> {
        return try {
            postDao.delete(post)
            Result.Success(null)
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override suspend fun updatePost(post: Post): Result<Post> {
        return try {
            postDao.update(post)
            Result.Success(post)
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override suspend fun getComments(postId: Int): Result<List<Comment>> {
        TODO("Not yet implemented")
    }
}