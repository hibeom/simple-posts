package com.pinkcloud.data.di

import com.pinkcloud.data.source.*
import com.pinkcloud.domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {

    @Binds
    @Named("local")
    abstract fun bindLocalPostDataSource(localPostDataSource: LocalPostDataSource): PostDataSource

    @Binds
    @Named("remote")
    abstract fun bindRemotePostDataSource(remotePostDataSource: RemotePostDataSource): PostDataSource

    @Binds
    abstract fun bindPostPagingDataSource(postPagingDataSource: BasePostPagingDataSource): PostPagingDataSource

    @Binds
    @Singleton
    abstract fun bindPostRepository(basePostRepository: BasePostRepository): PostRepository
}