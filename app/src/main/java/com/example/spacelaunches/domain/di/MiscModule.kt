package com.example.spacelaunches.domain.di

import android.content.Context
import android.media.MediaPlayer
import android.provider.Settings
import androidx.work.WorkManager
import com.example.spacelaunches.util.AndroidReminerScheduler
import com.example.spacelaunches.util.NotificationHelperImpl
import com.example.spacelaunches.util.Notificationhelper
import com.example.spacelaunches.util.ReminderScheduler
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MiscModule {

    companion object {


        @Provides
        @Singleton
        fun provideReminderScheduler(@ApplicationContext context: Context): ReminderScheduler {
            return AndroidReminerScheduler(context)
        }

        @Provides
        @Singleton
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        fun provideMediaPlayer(@ApplicationContext context: Context): MediaPlayer =
            MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI)

        @Singleton
        @Provides
        fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
            WorkManager.getInstance(context)
    }

        @Binds
        abstract fun bindNotificationHelper
            (notificationHelper: NotificationHelperImpl): Notificationhelper
}