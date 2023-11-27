package com.example.spacelaunches.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.spacelaunches.data.local.dao.ReminderDao
import com.example.spacelaunches.util.ReminderScheduler
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class DeviceBootReminderWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val androidReminderScheduler: ReminderScheduler,
    private val remindersDao: ReminderDao
):CoroutineWorker(appContext,params) {


    override suspend fun doWork(): Result {
         return  try {
             remindersDao.getReminders().collect{
                 it.forEach {
                     androidReminderScheduler.setReminder(it)
                 }
             }
             Result.success()
         } catch (e:Exception){
             e.printStackTrace()
             Result.failure()
         }
    }


}