package com.example.spacelaunches.broadcastReciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.spacelaunches.util.Constants
import com.example.spacelaunches.worker.DeviceBootReminderWorker
import javax.inject.Inject

class DeviceBootReciever :BroadcastReceiver() {

    @Inject
    lateinit var workManager: WorkManager




    override fun onReceive(p0: Context?, p1: Intent?) {
        if(p1?.action == Intent.ACTION_BOOT_COMPLETED){
            val workRequest
            = OneTimeWorkRequestBuilder<DeviceBootReminderWorker>().build()
            workManager.enqueueUniqueWork(
                Constants.SET_REMINDER_AFTER_DEVICE_BOOT,
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
        }

    }
}