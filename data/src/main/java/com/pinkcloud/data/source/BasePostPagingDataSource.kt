package com.pinkcloud.data.source

import androidx.paging.*
import com.pinkcloud.data.db.PostDatabase
import com.pinkcloud.data.model.asDomainModel
import com.pinkcloud.data.remote.PostService
import com.pinkcloud.domain.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
        ).flow.map { pagingData ->
            pagingData.map {
                it.asDomainModel()
            }
        }
    }
}