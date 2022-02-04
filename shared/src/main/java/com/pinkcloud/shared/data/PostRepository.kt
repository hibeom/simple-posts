package com.pinkcloud.shared.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pinkcloud.shared.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val localPostDataSource: PostPagingDataSource,
    @Named("local")
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
}