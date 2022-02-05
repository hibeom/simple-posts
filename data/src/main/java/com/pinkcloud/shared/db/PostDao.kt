package com.pinkcloud.shared.db

import androidx.paging.PagingSource
import androidx.room.*
import com.pinkcloud.shared.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Post>)

    @Query("SELECT * FROM posts")
    fun getPostPagingSource(): PagingSource<Int, Post>

    @Query("DELETE FROM posts")
    suspend fun clearAll()

    @Delete
    suspend fun delete(post: Post)

    @Update
    suspend fun update(post: Post)

    @Query("SELECT * FROM posts WHERE id = :postId")
    fun getPostFlow(postId: Int): Flow<Post>

    @Query("SELECT * FROM posts WHERE id = :postId")
    suspend fun getPost(postId: Int): Post
}