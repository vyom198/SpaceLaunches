package com.example.spacelaunches.domain.repostitory

import com.example.spacelaunches.data.local.entity.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepo {
    fun getReminders(): Flow<List<Reminder>>
    suspend fun cancelReminderFromDb(reminderId: String)
}