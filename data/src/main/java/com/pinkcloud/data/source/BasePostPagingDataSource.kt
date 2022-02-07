package com.pinkcloud.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pinkcloud.data.db.PostDatabase
import com.pinkcloud.data.model.Post
import com.pinkcloud.data.remote.PostService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BasePostPagingDataSource @Inject constructor(
    private val postDatabase: PostDatabase,
    private val postService: PostService
) : PostPagingDataSource {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPostPagingStream(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { postDatabase.postDao.getPostPagingSource() },
            remoteMediator = PostRemoteMediator(postDatabase, postService)
        ).flow
    }
}