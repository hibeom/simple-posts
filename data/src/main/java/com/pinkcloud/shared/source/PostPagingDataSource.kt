package com.pinkcloud.shared.source

import androidx.paging.PagingSource
import com.pinkcloud.shared.db.PostRemoteKey
import com.pinkcloud.shared.model.Post

interface PostPagingDataSource {

    fun getPostPagingSource(): PagingSource<Int, Post>

    suspend fun insertPosts(posts: List<Post>)

    suspend fun clearPosts()

    suspend fun insertRemoteKeys(remoteKey: List<PostRemoteKey>)

    suspend fun remoteKeyPostId(postId: Int): PostRemoteKey?

    suspend fun clearRemoteKey()
}