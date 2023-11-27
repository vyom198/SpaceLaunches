package com.example.spacelaunches.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.spacelaunches.data.local.LaunchDb
import com.example.spacelaunches.data.local.entity.LaunchEntity
import com.example.spacelaunches.data.local.entity.RemoteKey
import com.example.spacelaunches.util.toLaunchEntity
import com.example.spacelaunches.util.SafeApiRequest.safeApiRequest
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
const val INITIAL_PAGE = 0

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
            val newPageSize = 50
            val loadKey = when(loadType) {
                LoadType.REFRESH -> {

                    val remoteKey = getClosestRemoteKey(state)
                    remoteKey?.next?.minus(1) ?: INITIAL_PAGE

                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = false)
                LoadType.APPEND -> {
                    val remoteKey = getLastKey(state)
                    val nextPage = remoteKey?.next
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    nextPage
                }
            }
            Log.d("TAG", "initial log loadKey: ${loadKey} ")
            val allresultDto = safeApiRequest {
                launchapi.getAllUpcomingLaunches(
                    npage = loadKey * newPageSize,
                    pageCount = newPageSize
                )
            }
            Log.d("launchmediator",allresultDto.results.toString())

           launchDb.withTransaction {
               val endOfPagination = allresultDto.results.size < state.config.pageSize
                if(loadType == LoadType.REFRESH) {
                  launchDb.launchDao().clearAll()
                    launchDb.getRemotedao().nukeTable()
                }

               val prev = if (loadKey == INITIAL_PAGE) null else loadKey - 1
               val next = if (endOfPagination) null else loadKey + 1

                val LaunchEntities = allresultDto.results.map {
                    it.toLaunchEntity()
                }
             LaunchEntities.map {
                   launchDb.getRemotedao().insertKey(
                       RemoteKey(
                           prev = prev,
                           next = next,
                           id = it.id
                       )
                   )
               }
                  launchDb.launchDao().upsertAll(LaunchEntities)

            }

             return MediatorResult.Success(
                endOfPaginationReached = allresultDto.results.isEmpty()
            )
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
    private suspend fun getClosestRemoteKey(state: PagingState<Int, LaunchEntity>): RemoteKey? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let {
                launchDb.getRemotedao().getRemoteKey(it.id)
            }
        }
    }

    private suspend fun getLastKey(state: PagingState<Int, LaunchEntity>): RemoteKey? {
        return state.lastItemOrNull()?.let {
            launchDb.getRemotedao().getRemoteKey(it.id)
        }
    }

    }
