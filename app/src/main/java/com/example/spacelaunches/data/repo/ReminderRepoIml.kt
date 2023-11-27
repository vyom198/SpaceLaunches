package com.example.spacelaunches.data.repo

import com.example.spacelaunches.data.local.dao.ReminderDao
import com.example.spacelaunches.data.local.entity.Reminder
import com.example.spacelaunches.domain.repostitory.ReminderRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepoIml @Inject constructor(
    private  val reminderDao: ReminderDao
):ReminderRepo {
    override fun getReminders(): Flow<List<Reminder>> {
        return  reminderDao.getReminders()
    }

    override suspend fun cancelReminderFromDb(reminderId: String) {
        return reminderDao.deleteReminder(reminderId)
    }
}