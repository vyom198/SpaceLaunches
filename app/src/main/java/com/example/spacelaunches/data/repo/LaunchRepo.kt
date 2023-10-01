package com.example.spacelaunches.data.repo


import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.spacelaunches.data.local.LaunchDao
import com.example.spacelaunches.data.local.LaunchEntity

import com.example.spacelaunches.data.remote.LaunchRemoteMediator
import com.example.spacelaunches.domain.model.UpcomingLaunches
import com.example.spacelaunches.domain.repostitory.LaunchRepoiml
import com.example.spacelaunches.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LaunchRepo @Inject constructor(
    private val launchDao: LaunchDao,
    private  val remoteMediator: LaunchRemoteMediator
):LaunchRepoiml {
    @OptIn(ExperimentalPagingApi::class)
    override fun getUpcomingLaunches():
            Flow<PagingData<LaunchEntity>> {
      return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { launchDao.pagingSource() }
        ).flow

    }
    }


