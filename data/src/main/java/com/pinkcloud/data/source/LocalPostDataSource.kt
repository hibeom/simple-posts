package com.pinkcloud.data.source

import com.pinkcloud.data.db.PostDao
import com.pinkcloud.data.model.CommentEntity
import com.pinkcloud.data.model.PostEntity
import com.pinkcloud.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class LocalPostDataSource @Inject constructor(
    private val postDao: PostDao,
) : PostDataSource {

    override suspend fun getPost(postId: Int): Result<PostEntity> {
        return try {
            val post = postDao.getPost(postId)
            Result.Success(post)
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override fun getPostFlow(postId: Int): Flow<PostEntity> {
        return postDao.getPostFlow(postId).distinctUntilChanged()
    }

    override suspend fun deletePost(postEntity: PostEntity): Result<Void> {
        return try {
            postDao.delete(postEntity)
            Result.Success(null)
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override suspend fun updatePost(postEntity: PostEntity): Result<PostEntity> {
        return try {
            postDao.update(postEntity)
            Result.Success(postEntity)
        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }

    override suspend fun getComments(postId: Int): Result<List<CommentEntity>> {
        TODO("Not yet implemented")
    }
}