package com.pinkcloud.shared.data

import androidx.paging.PagingSource
import com.pinkcloud.shared.db.PostDao
import com.pinkcloud.shared.db.PostRemoteKey
import com.pinkcloud.shared.db.PostRemoteKeyDao
import com.pinkcloud.shared.model.Comment
import com.pinkcloud.shared.model.Post
import com.pinkcloud.shared.remote.Result
import javax.inject.Inject

class LocalPostDataSource @Inject constructor(
    private val postDao: PostDao,
    private val postRemoteKeyDao: PostRemoteKeyDao
) : PostDataSource, PostPagingDataSource {
    override suspend fun getPosts(start: Int, limit: Int): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getPost(postId: Int): Result<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(postId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePost(post: Post) {
        TODO("Not yet implemented")
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