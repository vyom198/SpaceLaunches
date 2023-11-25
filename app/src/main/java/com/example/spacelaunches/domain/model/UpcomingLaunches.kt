package com.example.spacelaunches.domain.model

import com.example.spacelaunches.data.model.LaunchServiceProvider_dto
import com.example.spacelaunches.data.model.Mission_dto
import com.example.spacelaunches.data.model.NetPrecision_dto
import com.example.spacelaunches.data.model.Pad_dto
import com.example.spacelaunches.data.model.Status_dto

data class UpcomingLaunches(
    val id: String,
    val image: String?,
    val last_updated: String?,
    val launch_service_provider: LaunchServiceProvider_dto?,
    val mission: Mission_dto?,
    val name: String?,
    val net: String?,
    val net_precision: NetPrecision_dto?,
    val pad: Pad_dto?,
    val status: Status_dto?,
    val window_end: String?,
    val window_start: String?,
    val url : String?


)
