package com.example.spacelaunches.domain.usecases

import com.example.spacelaunches.data.local.entity.Reminder
import com.example.spacelaunches.data.repo.ReminderRepoIml
import com.example.spacelaunches.util.ReminderScheduler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemindersUsecase @Inject constructor(
    private val reminderRepoIml: ReminderRepoIml,

){
    operator fun invoke () : Flow<List<Reminder>>{
        return reminderRepoIml.getReminders()
    }
}
