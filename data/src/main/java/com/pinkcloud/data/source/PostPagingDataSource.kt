package com.pinkcloud.data.source

import androidx.paging.PagingData
import com.pinkcloud.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostPagingDataSource {

    fun getPostPagingStream(): Flow<PagingData<Post>>
}