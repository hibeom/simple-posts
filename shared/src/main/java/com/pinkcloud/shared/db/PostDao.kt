package com.pinkcloud.shared.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pinkcloud.shared.model.Post

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Post>)

    @Query("SELECT * FROM posts")
    fun getPostPagingSource(): PagingSource<Int, Post>

    @Query("DELETE FROM posts")
    suspend fun clearAll()
}