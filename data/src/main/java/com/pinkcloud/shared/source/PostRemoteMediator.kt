package com.pinkcloud.shared.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.pinkcloud.shared.db.PostDatabase
import com.pinkcloud.shared.db.PostRemoteKey
import com.pinkcloud.shared.model.Post
import retrofit2.HttpException
import java.io.IOException

private const val START_PAGE_INDEX = 0
const val PAGE_SIZE = 20

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val localPostDataSource: PostPagingDataSource,
    private val remotePostDataSource: PostDataSource,
    private val database: PostDatabase
) : RemoteMediator<Int, Post>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Post>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> START_PAGE_INDEX
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                nextKey
            }
        }

        try {
            val posts = remotePostDataSource.getPosts(page*state.config.pageSize, state.config.pageSize)
            val endOfPaginationReached = posts.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localPostDataSource.clearRemoteKey()
                    localPostDataSource.clearPosts()
                }
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = posts.map {
                    PostRemoteKey(it.id, nextKey)
                }
                localPostDataSource.insertRemoteKeys(keys)
                localPostDataSource.insertPosts(posts)
            }
            return MediatorResult.Success(endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Post>): PostRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { post ->
                database.withTransaction {
                    localPostDataSource.remoteKeyPostId(post.id)
                }
            }
    }
}