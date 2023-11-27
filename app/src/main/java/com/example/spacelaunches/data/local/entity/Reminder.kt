package com.example.spacelaunches.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.spacelaunches.data.model.LaunchServiceProvider_dto
import com.example.spacelaunches.data.model.Mission_dto
import com.example.spacelaunches.data.model.NetPrecision_dto
import com.example.spacelaunches.data.model.Pad_dto
import com.example.spacelaunches.data.model.Status_dto

@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = false)
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
val url: String?,
val window_end: String?,
val window_start: String?

)
