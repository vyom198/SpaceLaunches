package com.example.spacelaunches.di

import android.content.Context
import androidx.room.Room
import com.example.spacelaunches.data.local.dao.LaunchDao
import com.example.spacelaunches.data.local.LaunchDb
import com.example.spacelaunches.data.local.dao.ReminderDao
import com.example.spacelaunches.data.local.dao.RemoteKeyDao
import com.example.spacelaunches.data.remote.LaunchRemoteMediator
import com.example.spacelaunches.data.remote.SpaceapiInterface
import com.example.spacelaunches.data.repo.LaunchRepoIml
import com.example.spacelaunches.data.repo.ReminderRepoIml
import com.example.spacelaunches.domain.repostitory.LaunchRepo
import com.example.spacelaunches.domain.repostitory.ReminderRepo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun LaunchapiService():SpaceapiInterface{
        return Retrofit.Builder().baseUrl(SpaceapiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(
              SpaceapiInterface::class.java
            )

    }
    @Provides
    @Singleton
    fun LaunchdbService(@ApplicationContext context: Context):LaunchDb{
        return Room.databaseBuilder(context=context, LaunchDb::class.java,"LaunchDatabase")
            .fallbackToDestructiveMigration().build()
    }
    @Provides
    @Singleton
    fun provideLaunchDao (launchDb: LaunchDb): LaunchDao {
        return launchDb.launchDao()
    }
    @Provides
    @Singleton
    fun provideremoteDao ( launchDb: LaunchDb): RemoteKeyDao {
        return launchDb.getRemotedao()
    }

    @Provides
    @Singleton
    fun provideReminderDao (launchDb: LaunchDb): ReminderDao {
        return launchDb.getReminderdao()
    }
    @Provides
    @Singleton
    fun provideRemotelaunchMediator (launchDb: LaunchDb,api:SpaceapiInterface )
    :LaunchRemoteMediator{
        return LaunchRemoteMediator(launchDb,api)
    }
    @Provides
    @Singleton
    fun providelaunchRepository(
        launchDao: LaunchDao,
        remoteMediator: LaunchRemoteMediator
    ): LaunchRepo {
        return LaunchRepoIml(launchDao,remoteMediator)

    }
    @Provides
    @Singleton
    fun provideReminderRepo(
        reminderDao: ReminderDao
    ): ReminderRepo {
        return ReminderRepoIml(reminderDao)

    }
}