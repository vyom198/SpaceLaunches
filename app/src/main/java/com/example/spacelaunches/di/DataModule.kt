package com.example.spacelaunches.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.spacelaunches.data.local.LaunchDao
import com.example.spacelaunches.data.local.LaunchDb
import com.example.spacelaunches.data.remote.LaunchRemoteMediator
import com.example.spacelaunches.data.remote.SpaceapiInterface
import com.example.spacelaunches.data.repo.LaunchRepo
import com.example.spacelaunches.domain.repostitory.LaunchRepoiml
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
    fun provideLaunchDao (launchDb: LaunchDb):LaunchDao{
        return launchDb.launchDao()
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
    ):LaunchRepoiml {
        return LaunchRepo(launchDao,remoteMediator)

    }
}