package com.example.spacelaunches.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface LaunchDao {
    @Upsert
    suspend fun upsertAll(LaunchEntity: List<LaunchEntity>)

    @Query("SELECT * FROM launchentity")
    fun pagingSource(): PagingSource<Int, LaunchEntity>

    @Query("DELETE FROM launchentity")
    suspend fun clearAll()
}