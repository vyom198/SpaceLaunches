package com.example.spacelaunches.domain.repostitory

import androidx.paging.PagingData
import com.example.spacelaunches.data.local.entity.LaunchEntity
import com.example.spacelaunches.data.local.entity.Reminder
import kotlinx.coroutines.flow.Flow

interface LaunchRepo {
    fun getUpcomingLaunches(): Flow<PagingData<LaunchEntity>>
    suspend fun saveReminder(reminder: Reminder)
}
