package com.example.spacelaunches.domain.usecases

import com.example.spacelaunches.data.repo.ReminderRepoIml
import com.example.spacelaunches.util.ReminderScheduler
import com.example.spacelaunches.util.Resource
import javax.inject.Inject

class CancelReminderUsecase @Inject constructor(
    private  val reminderRepoIml: ReminderRepoIml,
    private val CancelreminderScheduler: ReminderScheduler
){
    suspend operator fun invoke(id: String): Resource<Nothing?> {
        return try {
            CancelreminderScheduler.cancelReminder(id)
            reminderRepoIml.cancelReminderFromDb(id)
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage)
        }
    }
}