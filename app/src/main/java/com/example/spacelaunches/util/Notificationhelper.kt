package com.example.spacelaunches.util

interface Notificationhelper {

    fun createNotificationChannel(
        name: String,
        id: String,
        importance: Int,
        vibratePattern: LongArray? = null
    )

    fun showNotification(
        channelId: String,
        notificationId: Int,
        title: String,
        content: String
    )
}