package com.pinkcloud.shared.source

import androidx.paging.PagingSource
import com.pinkcloud.shared.db.PostDao
import com.pinkcloud.shared.db.PostRemoteKey
import com.pinkcloud.shared.db.PostRemoteKeyDao
import com.pinkcloud.shared.model.Comment
import com.pinkcloud.shared.model.Post
import com.pinkcloud.shared.remote.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class LocalPostDataSource @Inject constructor(
    private val postDao: PostDao,
    private val postRemoteKeyDao: PostRemoteKeyDao
) : PostDataSource, PostPagingDataSource {
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

    override fun getPostPagingSource(): PagingSource<Int, Post> {
        return postDao.getPostPagingSource()
    }

    override suspend fun insertPosts(posts: List<Post>) {
        postDao.insertAll(posts)
    }

    override suspend fun clearPosts() {
        postDao.clearAll()
    }

    override suspend fun insertRemoteKeys(remoteKey: List<PostRemoteKey>) {
        postRemoteKeyDao.insertAll(remoteKey)
    }

    override suspend fun remoteKeyPostId(postId: Int): PostRemoteKey? {
        return postRemoteKeyDao.remoteKeyPostId(postId)
    }

    override suspend fun clearRemoteKey() {
        postRemoteKeyDao.clearRemoteKey()
    }
}