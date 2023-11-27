package com.example.spacelaunches.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.spacelaunches.MainActivity
import com.example.spacelaunches.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHelperImpl @Inject constructor(
    @ApplicationContext private  val context: Context
):Notificationhelper {


    @SuppressLint("NewApi")
    override fun createNotificationChannel(
        name: String,
        id: String,
        importance: Int,
        vibratePattern: LongArray?
    ) {
        val channel = NotificationChannel(
            id,
            name,
            importance
        )
        vibratePattern?.let {
           channel.enableVibration(true)
            channel.vibrationPattern = vibratePattern
        }
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    override fun showNotification(
        channelId: String,
        notificationId: Int,
        title: String,
        content: String
    ) {
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = context?.let {
            NotificationCompat.Builder(it, channelId)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        }

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(notificationId, builder?.build())
    }
    }
