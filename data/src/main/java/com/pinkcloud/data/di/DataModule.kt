package com.pinkcloud.data.di

import com.pinkcloud.data.source.local.BaseLocalPostDataSource
import com.pinkcloud.data.source.BasePostRepository
import com.pinkcloud.data.source.local.LocalPostDataSource
import com.pinkcloud.data.source.paging.BasePostPagingDataSource
import com.pinkcloud.data.source.paging.PostPagingDataSource
import com.pinkcloud.data.source.remote.BaseRemotePostDataSource
import com.pinkcloud.data.source.remote.RemotePostDataSource
import com.pinkcloud.domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindLocalPostDataSource(baseLocalPostDataSource: BaseLocalPostDataSource): LocalPostDataSource

    @Binds
    abstract fun bindRemotePostDataSource(baseRemotePostDataSource: BaseRemotePostDataSource): RemotePostDataSource

    @Binds
    abstract fun bindPostPagingDataSource(postPagingDataSource: BasePostPagingDataSource): PostPagingDataSource

    @Binds
    @Singleton
    abstract fun bindPostRepository(basePostRepository: BasePostRepository): PostRepository
}