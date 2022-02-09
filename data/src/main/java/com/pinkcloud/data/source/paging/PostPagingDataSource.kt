package com.pinkcloud.data.source.paging

import androidx.paging.PagingData
import com.pinkcloud.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostPagingDataSource {

    fun getPostPagingStream(): Flow<PagingData<Post>>
}