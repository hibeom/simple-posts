package com.pinkcloud.shared.di

import com.pinkcloud.shared.remote.PostService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    @Singleton
    @Provides
    fun providePostService(): PostService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val moshiConverterFactory = MoshiConverterFactory.create(moshi)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .build()
            .create(PostService::class.java)
    }
}