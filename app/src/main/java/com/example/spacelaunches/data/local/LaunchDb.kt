package com.example.spacelaunches.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spacelaunches.data.converters.Converters
import com.example.spacelaunches.data.model.LaunchServiceProvider_dto

@Database(
    entities = [LaunchEntity::class,RemoteKey::class],
     version = 3
)
@TypeConverters(
    Converters::class
)
 abstract class LaunchDb : RoomDatabase()

 {
    abstract  fun launchDao (): LaunchDao
    abstract fun   getRemotedao ():RemoteKeyDao
}