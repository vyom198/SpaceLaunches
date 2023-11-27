package com.example.spacelaunches.util

import com.example.spacelaunches.data.local.entity.Reminder


interface ReminderScheduler {
    fun setReminder(reminder: Reminder):SchedulerState
    fun cancelReminder(id: String)
}

sealed class SchedulerState ( val error : String ? = null){
 data class  PermissionState(
     val reminderPermission : Boolean,
     val notificationPermission : Boolean
 ):SchedulerState()

     object SetSuccessfully :SchedulerState()
     data class NotSet(val message : String) : SchedulerState(message)
}