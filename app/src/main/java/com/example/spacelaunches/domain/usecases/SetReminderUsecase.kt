package com.example.spacelaunches.domain.usecases

import com.example.spacelaunches.data.local.entity.Reminder
import com.example.spacelaunches.data.repo.LaunchRepoIml
import com.example.spacelaunches.data.repo.ReminderRepoIml
import com.example.spacelaunches.presentation.states.ReminderState
import com.example.spacelaunches.util.Constants
import com.example.spacelaunches.util.ReminderScheduler
import com.example.spacelaunches.util.Resource
import com.example.spacelaunches.util.SchedulerState
import javax.inject.Inject

class SetReminderUsecase @Inject constructor(
    private  val launchRepoIml: LaunchRepoIml,
    private  val androidReminderScheduler: ReminderScheduler
) {
   suspend operator fun invoke (reminder: Reminder):Resource<Nothing?>{
        return try {
            val reminderState = androidReminderScheduler.setReminder(reminder)
            return when (reminderState) {
                SchedulerState.SetSuccessfully -> {
                    launchRepoIml.saveReminder(reminder)
                    Resource.Success(null)
                }

                is SchedulerState.NotSet -> {
                    Resource.Error(message = reminderState.error!!)
                }

                is SchedulerState.PermissionState -> {
                    when {
                        reminderState.reminderPermission && !reminderState.notificationPermission -> {
                            Resource.Error(
                                message = Constants.NOTIFICATION_PERMISSION_NOT_AVAILABLE
                            )
                        }

                        !reminderState.reminderPermission && reminderState.notificationPermission -> {
                            Resource.Error(message = Constants.REMINDER_PERMISSION_NOT_AVAILABLE)
                        }

                        !reminderState.reminderPermission && !reminderState.notificationPermission -> {
                            Resource.Error(
                                message = Constants.NOTIFICATION_REMINDER_PERMISSION_NOT_AVAILABLE
                            )
                        }

                        else -> {
                            Resource.Error(
                                message = Constants.NOTIFICATION_REMINDER_PERMISSION_NOT_AVAILABLE
                            )
                        }
                    }
                }
            }

        }catch (e:Exception){
            return Resource.Error(e.localizedMessage)
        }
    }
}