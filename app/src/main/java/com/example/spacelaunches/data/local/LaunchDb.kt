package com.example.spacelaunches.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spacelaunches.data.converters.Converters
import com.example.spacelaunches.data.local.dao.LaunchDao
import com.example.spacelaunches.data.local.dao.ReminderDao
import com.example.spacelaunches.data.local.dao.RemoteKeyDao
import com.example.spacelaunches.data.local.entity.LaunchEntity

import com.example.spacelaunches.data.local.entity.Reminder
import com.example.spacelaunches.data.local.entity.RemoteKey

@Database(
    entities = [LaunchEntity::class, RemoteKey::class, Reminder::class],

    version = 5
)
@TypeConverters(
    Converters::class
)
 abstract class LaunchDb : RoomDatabase()

 {
    abstract  fun launchDao (): LaunchDao
    abstract fun   getRemotedao (): RemoteKeyDao
    abstract fun  getReminderdao () :ReminderDao
}