package com.example.spacelaunches.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.spacelaunches.data.local.entity.LaunchEntity
import com.example.spacelaunches.data.local.entity.Reminder

@Dao
interface LaunchDao {
    @Upsert
    suspend fun upsertAll(LaunchEntity: List<LaunchEntity>)

    @Query("SELECT * FROM launchentity")
    fun pagingSource(): PagingSource<Int, LaunchEntity>

    @Query("DELETE FROM launchentity")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReminder(launch: Reminder)
}