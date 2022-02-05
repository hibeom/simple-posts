package com.pinkcloud.shared.data

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
    private val localPostDataSource: PostPagingDataSource,
    @Named("remote")
    private val remotePostDataSource: PostDataSource
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPostPagingFlow(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { localPostDataSource.getPostPagingSource() },
            remoteMediator = PostRemoteMediator(localPostDataSource, remotePostDataSource)
        ).flow
    }

    suspend fun getPost(postId: Int): Result<Post> {
        return remotePostDataSource.getPost(postId)
    }

    suspend fun getComments(postId: Int): Result<List<Comment>> {
        return remotePostDataSource.getComments(postId)
    }
}