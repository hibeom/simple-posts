package com.pinkcloud.data.di

import com.pinkcloud.data.source.*
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
    @Singleton
    abstract fun bindLocalPostDataSource(localPostDataSource: LocalPostDataSource): PostDataSource

    @Binds
    @Named("remote")
    @Singleton
    abstract fun bindRemotePostDataSource(remotePostDataSource: RemotePostDataSource): PostDataSource

    @Binds
    abstract fun bindPostPagingDataSource(postPagingDataSource: BasePostPagingDataSource): PostPagingDataSource
}