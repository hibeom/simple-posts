package com.pinkcloud.shared.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pinkcloud.shared.model.Comment
import com.pinkcloud.shared.model.Post
import com.pinkcloud.shared.remote.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val localPostPagingDataSource: PostPagingDataSource,
    @Named("remote")
    private val remotePostDataSource: PostDataSource,
    @Named("local")
    private val localPostDataSource: PostDataSource
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPostPagingFlow(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { localPostPagingDataSource.getPostPagingSource() },
            remoteMediator = PostRemoteMediator(localPostPagingDataSource, remotePostDataSource)
        ).flow
    }

    suspend fun getPost(postId: Int): Result<Post> {
        return localPostDataSource.getPost(postId)
    }

    fun getPostFlow(postId: Int): Flow<Post> = localPostDataSource.getPostFlow(postId)

    suspend fun getComments(postId: Int): Result<List<Comment>> {
        return remotePostDataSource.getComments(postId)
    }

    suspend fun deletePost(post: Post): Result<Void> {
        return remotePostDataSource.deletePost(post).let { result ->
            if (result is Result.Success) localPostDataSource.deletePost(post)
            else result
        }
    }

    suspend fun updatePost(post: Post): Result<Post> {
        return remotePostDataSource.updatePost(post).let { result ->
            if (result is Result.Success) localPostDataSource.updatePost(post)
            else result
        }
    }
}