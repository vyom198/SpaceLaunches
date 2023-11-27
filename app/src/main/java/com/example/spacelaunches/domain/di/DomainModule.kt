package com.example.spacelaunches.domain.di

import com.example.spacelaunches.data.repo.LaunchRepoIml
import com.example.spacelaunches.data.repo.ReminderRepoIml
import com.example.spacelaunches.domain.usecases.CancelReminderUsecase
import com.example.spacelaunches.domain.usecases.GetRemindersUsecase
import com.example.spacelaunches.domain.usecases.SetReminderUsecase
import com.example.spacelaunches.util.ReminderScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun provideCancelReminderUsecase(
        reminderRepo:ReminderRepoIml,
        reminderScheduler: ReminderScheduler
    ):CancelReminderUsecase {
        return CancelReminderUsecase(reminderRepo,reminderScheduler)
    }

    @Provides
    @Singleton
    fun provideGetReminderUsecase(
        reminderRepo:ReminderRepoIml
    ):GetRemindersUsecase{
        return GetRemindersUsecase(reminderRepo)
    }
    @Provides
    @Singleton
    fun provideSetReminderUsecase(
        launchRepoIml: LaunchRepoIml,
        reminderScheduler: ReminderScheduler
    ): SetReminderUsecase {
        return SetReminderUsecase(launchRepoIml,reminderScheduler)
    }


}