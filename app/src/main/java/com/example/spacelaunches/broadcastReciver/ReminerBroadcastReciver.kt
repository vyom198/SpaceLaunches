package com.example.spacelaunches.broadcastReciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.ui.unit.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.spacelaunches.util.Constants
import com.example.spacelaunches.worker.ReminderNotificationCleanUpWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReminderBroadcastReceiver : BroadcastReceiver()  {

    @Inject
    lateinit var workManager: WorkManager

    override fun onReceive(p0: Context?, p1: Intent?) {

      val launchName = p1?.getStringExtra(Constants.KEY_LAUNCH_NAME)
        val launchId = p1?.getStringExtra(Constants.KEY_LAUNCH_ID)

        val workRequest =
            OneTimeWorkRequestBuilder<ReminderNotificationCleanUpWorker>()
                .setInputData(
                   workDataOf(
                       Constants.KEY_LAUNCH_NAME to launchName,
                       Constants.KEY_LAUNCH_ID to launchId
                   )
                ).build()
        workManager.enqueueUniqueWork(
         Constants.REMINDER_NOTIFICATION_AND_CLEANUP,
            ExistingWorkPolicy.APPEND_OR_REPLACE,
              workRequest
        )
    }
}