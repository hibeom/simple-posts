package com.pinkcloud.data.source

import com.pinkcloud.data.db.entity.asDomainModel
import com.pinkcloud.data.source.local.LocalPostDataSource
import com.pinkcloud.data.source.paging.PostPagingDataSource
import com.pinkcloud.data.source.remote.RemotePostDataSource
import com.pinkcloud.data.utils.asDomainModel
import com.pinkcloud.data.utils.asEntity
import com.pinkcloud.domain.model.Comment
import com.pinkcloud.domain.model.Post
import com.pinkcloud.domain.repository.PostRepository
import com.pinkcloud.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BasePostRepository @Inject constructor(
    private val postPagingDataSource: PostPagingDataSource,
    private val remotePostDataSource: RemotePostDataSource,
    private val localPostDataSource: LocalPostDataSource,
) : PostRepository {

    override fun getPostPagingFlow() = postPagingDataSource.getPostPagingStream()

    override suspend fun getPost(postId: Int): Result<Post> {
        return localPostDataSource.getPost(postId).asDomainModel()
    }

    override fun getPostFlow(postId: Int): Flow<Post?> {
        return localPostDataSource.getPostFlow(postId).map { entity ->
            entity?.asDomainModel()
        }
    }

    override suspend fun getComments(postId: Int): Result<List<Comment>> {
        return remotePostDataSource.getComments(postId).asDomainModel()
    }

    override suspend fun deletePost(post: Post): Result<Void> {
        val postEntity = post.asEntity()
        return remotePostDataSource.deletePost(postEntity).let { result ->
            if (result is Result.Success) localPostDataSource.deletePost(postEntity)
            else result
        }
    }

    override suspend fun updatePost(post: Post): Result<Post> {
        val postEntity = post.asEntity()
        return remotePostDataSource.updatePost(postEntity).let { result ->
            if (result is Result.Success) localPostDataSource.updatePost(postEntity)
            else result
        }.asDomainModel()
    }
}