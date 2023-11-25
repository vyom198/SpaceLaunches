package com.example.spacelaunches.data.repo


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.spacelaunches.data.local.dao.LaunchDao
import com.example.spacelaunches.data.local.dao.ReminderDao
import com.example.spacelaunches.data.local.entity.LaunchEntity
import com.example.spacelaunches.data.local.entity.Reminder

import com.example.spacelaunches.data.remote.LaunchRemoteMediator
import com.example.spacelaunches.domain.repostitory.LaunchRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LaunchRepoIml @Inject constructor(
    private val launchDao: LaunchDao,

    private  val remoteMediator: LaunchRemoteMediator
): LaunchRepo {
    @OptIn(ExperimentalPagingApi::class)
    override fun getUpcomingLaunches():
            Flow<PagingData<LaunchEntity>> {
      return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { launchDao.pagingSource() }
        ).flow

    }

    override suspend fun saveReminder(reminder: Reminder) {
     launchDao.saveReminder(reminder)
    }
}


