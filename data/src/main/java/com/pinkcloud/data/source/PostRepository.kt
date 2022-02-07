package com.pinkcloud.data.source

import com.pinkcloud.data.model.Comment
import com.pinkcloud.data.model.Post
import com.pinkcloud.data.remote.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val postPagingDataSource: PostPagingDataSource,
    @Named("remote")
    private val remotePostDataSource: PostDataSource,
    @Named("local")
    private val localPostDataSource: PostDataSource,
) {

    fun getPostPagingFlow() = postPagingDataSource.getPostPagingStream()

    suspend fun getPost(postId: Int): Result<Post> {
        return localPostDataSource.getPost(postId)
    }

    fun getPostFlow(postId: Int): Flow<Post> = localPostDataSource.getPostFlow(postId)

    suspend fun getComments(postId: Int): Result<List<Comment>> {
        return remotePostDataSource.getComments(postId)
    }

    suspend fun deletePost(post: Post): Result<Void> {
        return remotePostDataSource.deletePost(post).let { result ->
            if (result is Result.Success) localPostDataSource.deletePost(post)
            else result
        }
    }

    suspend fun updatePost(post: Post): Result<Post> {
        return remotePostDataSource.updatePost(post).let { result ->
            if (result is Result.Success) localPostDataSource.updatePost(post)
            else result
        }
    }
}