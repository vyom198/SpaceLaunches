package com.example.spacelaunches.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.spacelaunches.data.local.entity.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

        @Query("SELECT * FROM reminders")
        fun getReminders(): Flow<List<Reminder>>

        @Query("DELETE FROM reminders WHERE id =:reminderId")
        suspend fun deleteReminder(reminderId: String)


}