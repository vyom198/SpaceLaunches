package com.example.spacelaunches.util
//import com.example.spacelaunches.
import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.material.ScrollableTabRow
import androidx.core.content.ContextCompat
import androidx.core.os.BuildCompat
import com.example.spacelaunches.R
import com.example.spacelaunches.broadcastReciver.ReminderBroadcastReceiver
import com.example.spacelaunches.data.local.entity.Reminder
import com.example.spacelaunches.util.Helpers.Companion.isNull
import com.example.spacelaunches.util.Helpers.Companion.toDate
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidReminerScheduler @Inject constructor(
    @ApplicationContext private val context: Context
):ReminderScheduler {
    val alarmManager = context.getSystemService(AlarmManager::class.java)

    /** Apps targeting Android version 12 (Version Code - S, Api Level 31)
    // or higher need to request for SCHEDULE_EXACT_ALARM permission explicitly whereas
    // Apps targeting any Android version below 12 don't need to do ask for this permission

    // Apps targeting Android version 13 (Version Code - Tiramisu, Api Level 33)
    // or higher need to request for POST_NOTIFICATIONS permission explicitly whereas
    // Apps targeting any Android version below 13 don't need to do ask for this permission

    // 1. For 13 and above both SCHEDULE_EXACT_ALARM and POST_NOTIFICATIONS permission needs to be checked
    // 2. For 12, 12L SCHEDULE_EXACT_ALARM needs to checked
    // 3. For below 12 no neither needs to be checked
    **/

    override fun setReminder(reminder: Reminder): SchedulerState {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
          return  when {
              alarmManager.canScheduleExactAlarms() && areNotificationsEnabled(context) ->{
                 return setAlarm(reminder).let { errormessage->
                   if (errormessage.isNull()){
                      SchedulerState.SetSuccessfully
                   }else{
                       SchedulerState.NotSet(errormessage!!)
                   }
                 }
              }
              alarmManager.canScheduleExactAlarms() && !areNotificationsEnabled(context) ->{
                  SchedulerState.PermissionState(
                      reminderPermission = true,
                      notificationPermission = false
                  )
              }
             !alarmManager.canScheduleExactAlarms() && areNotificationsEnabled(context) ->{
                 SchedulerState.PermissionState(
                     reminderPermission = false,
                     notificationPermission = true
                 )
              }
              alarmManager.canScheduleExactAlarms() && areNotificationsEnabled(context) ->{
                  SchedulerState.PermissionState(
                      reminderPermission = false,
                      notificationPermission = false
                  )
              }
              else ->{
                  SchedulerState.PermissionState(
                      reminderPermission = false,
                      notificationPermission = false
                  )
              }
          }


        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return if (alarmManager.canScheduleExactAlarms()) {
                return setAlarm(reminder).let { errorMessage ->
                    if (errorMessage.isNull()) {
                        SchedulerState.SetSuccessfully
                    } else {
                        SchedulerState.NotSet(errorMessage!!)
                    }
                }
            } else {
                SchedulerState.PermissionState(
                    reminderPermission = false,
                    notificationPermission = true
                )
            }
        } else {
            return setAlarm(reminder).let { errorMessage ->
                if (errorMessage.isNull()) {
                   SchedulerState.SetSuccessfully
                } else {
                    SchedulerState.NotSet(errorMessage!!)
                }
            }
        }
    }

    override fun cancelReminder(id: String) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.hashCode(),
            Intent(context, ReminderBroadcastReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(
            pendingIntent
        )
    }




    // returns null if the reminder was set successfully otherwise error message
    private fun setAlarm(reminder: Reminder): String? {
        val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
            putExtra(Constants.KEY_LAUNCH_NAME, reminder.name)
            putExtra(Constants.KEY_LAUNCH_ID, reminder.id)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val launchTime = reminder.net?.toDate(Constants.LAUNCH_DATE_INPUT_FORMAT)!!.time

        // In release mode the reminder will be scheduled for 10 minutes before the
        // launch time.

        // In order to test whether the reminder functionality is working
        // reminder is set after 10 seconds from current time in Debug mode
//        val reminderTime = if () {
//            launchTime - Constants.TEN_MINUTES_IN_MILLIS
//        } else {
//            System.currentTimeMillis() + 10_000
//        }
       val reminderTime = launchTime-Constants.TEN_MINUTES_IN_MILLIS
        return if (launchTime < System.currentTimeMillis()) {
            context.getString(R.string.launch_time_passed)
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                reminderTime,
                pendingIntent
            )
            null
        }
    }

    @SuppressLint("InlinedApi")
    private fun areNotificationsEnabled(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }
}