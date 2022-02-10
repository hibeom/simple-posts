package com.pinkcloud.data.db

import androidx.paging.PagingSource
import androidx.room.*
import com.pinkcloud.data.db.entity.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(postEntities: List<PostEntity>)

    @Query("SELECT * FROM posts")
    fun getPostPagingSource(): PagingSource<Int, PostEntity>

    @Query("DELETE FROM posts")
    suspend fun clearAll()

    @Delete
    suspend fun delete(postEntity: PostEntity)

    @Update
    suspend fun update(postEntity: PostEntity)

    @Query("SELECT * FROM posts WHERE id = :postId")
    fun getPostFlow(postId: Int): Flow<PostEntity?>

    @Query("SELECT * FROM posts WHERE id = :postId")
    suspend fun getPost(postId: Int): PostEntity
}