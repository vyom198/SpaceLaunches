package com.example.spacelaunches.data.remote

import com.example.spacelaunches.data.model.Allresult_dto
import com.example.spacelaunches.data.model.UpcomingLaunch_dto
import com.example.spacelaunches.domain.model.UpcomingLaunches
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceapiInterface {
    @GET("/2.2.0/launch/upcoming")
    suspend fun getAllUpcomingLaunches (
        @Query("offset") npage: Int,
        @Query("limit") pageCount: Int
       ):Response<Allresult_dto>
    companion object {
        const val BASE_URL = "https://ll.thespacedevs.com"
    }
}