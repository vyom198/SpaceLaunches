package com.example.spacelaunches.worker

import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.spacelaunches.data.local.dao.ReminderDao
import com.example.spacelaunches.util.Constants
import com.example.spacelaunches.util.Notificationhelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

@HiltWorker
class ReminderNotificationCleanUpWorker  @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val mediaPlayer: MediaPlayer,
    private val notificationHelper: Notificationhelper,
    private val remindersDao: ReminderDao
):CoroutineWorker(appContext,params){
    override suspend fun doWork(): Result {
       return try {
           val launchName = inputData.getString(Constants.KEY_LAUNCH_NAME)
           val launchId = inputData.getString(Constants.KEY_LAUNCH_ID)

           withContext(Dispatchers.Main) {
               mediaPlayer.start()
               showNotification(launchName)
               delay(Constants.REMINDER_SOUND_DURATION)
               mediaPlayer.stop()
           }
           launchId?.let { remindersDao.deleteReminder(it) }
           Result.success()
       } catch (e: Exception) {
           e.printStackTrace()
           withContext(Dispatchers.Main) {
               mediaPlayer.stop()
           }
           Result.failure()
       }
       }

    private fun showNotification(launchName: String?) {
      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
          notificationHelper.createNotificationChannel(
              name = Constants.REMINDER_NOTIFICATION_CHANNEL,
              id =Constants.REMINDER_CHANNEL_ID,
              importance = NotificationManager.IMPORTANCE_HIGH
          )
          notificationHelper.showNotification(
              channelId = Constants.REMINDER_CHANNEL_ID,
              notificationId = Random.nextInt(),
              title = "Reminder From Space Launches",
              content = "$launchName mission is about to launch"
          )
      }

    }
}


