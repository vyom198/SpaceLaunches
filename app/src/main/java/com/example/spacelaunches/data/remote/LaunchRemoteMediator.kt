package com.example.spacelaunches.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.spacelaunches.data.local.LaunchDb
import com.example.spacelaunches.data.local.LaunchEntity
import com.example.spacelaunches.data.local.toLaunchEntity
import com.example.spacelaunches.util.SafeApiRequest
import com.example.spacelaunches.util.SafeApiRequest.safeApiRequest
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class LaunchRemoteMediator @Inject constructor(
    private  val launchDb: LaunchDb,
    private val launchapi : SpaceapiInterface
) : RemoteMediator<Int, LaunchEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LaunchEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        0
                    } else {
                        (lastItem.id1 / state.config.pageSize) + 1
                    }
                }
            }

            val allresultDto = safeApiRequest {
                launchapi.getAllUpcomingLaunches(
                    npage = loadKey * state.config.pageSize,
                    pageCount = state.config.pageSize
                )
            }
            Log.d("launchmediator",allresultDto.results.toString())
           launchDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                  launchDb.launchDao().clearAll()
                }
                val LaunchEntities = allresultDto.results.map {
                    it.toLaunchEntity()
                }
                  launchDb.launchDao().upsertAll(LaunchEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = allresultDto.results.isEmpty()
            )
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    }
