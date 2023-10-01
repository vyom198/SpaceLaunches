package com.example.spacelaunches.domain.repostitory

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.spacelaunches.data.local.LaunchEntity
import com.example.spacelaunches.domain.model.UpcomingLaunches
import com.example.spacelaunches.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LaunchRepoiml {
    fun getUpcomingLaunches(): Flow<PagingData<LaunchEntity>>

}