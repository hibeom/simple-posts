package com.pinkcloud.shared.di

import com.pinkcloud.shared.data.LocalPostDataSource
import com.pinkcloud.shared.data.PostDataSource
import com.pinkcloud.shared.data.PostPagingDataSource
import com.pinkcloud.shared.data.RemotePostDataSource
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
    @Singleton
    abstract fun bindPostPagingDataSource(postPagingDataSource: LocalPostDataSource): PostPagingDataSource
}