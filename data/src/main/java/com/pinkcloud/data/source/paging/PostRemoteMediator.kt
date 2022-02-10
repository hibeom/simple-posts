package com.pinkcloud.data.source.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.pinkcloud.data.db.PostDatabase
import com.pinkcloud.data.db.PostRemoteKey
import com.pinkcloud.data.db.entity.PostEntity
import com.pinkcloud.data.api.PostService
import com.pinkcloud.data.api.asEntity
import retrofit2.HttpException
import java.io.IOException

private const val START_PAGE_INDEX = 0
const val PAGE_SIZE = 20

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val database: PostDatabase,
    private val postService: PostService
) : RemoteMediator<Int, PostEntity>() {

    private val postDao = database.postDao
    private val postRemoteKeyDao = database.postRemoteKeyDao

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PostEntity>): MediatorResult {
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
            val posts = postService
                .getPostsPage(page * state.config.pageSize, state.config.pageSize)
                .map {
                    it.asEntity()
                }
            val endOfPaginationReached = posts.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    postRemoteKeyDao.clearRemoteKey()
                    postDao.clearAll()
                }
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = posts.map {
                    PostRemoteKey(it.id, nextKey)
                }
                postRemoteKeyDao.insertAll(keys)
                postDao.insertAll(posts)
            }
            return MediatorResult.Success(endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PostEntity>): PostRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { post ->
                database.withTransaction {
                    postRemoteKeyDao.remoteKeyPostId(post.id)
                }
            }
    }
}