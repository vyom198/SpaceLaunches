package com.example.spacelaunches.util

import com.example.spacelaunches.data.local.entity.LaunchEntity
import com.example.spacelaunches.data.local.entity.Reminder
import com.example.spacelaunches.data.model.UpcomingLaunch_dto
import com.example.spacelaunches.domain.model.UpcomingLaunches

fun UpcomingLaunch_dto.toLaunchEntity(): LaunchEntity {
    return LaunchEntity(
        id = id,
        image = image,
        last_updated = last_updated,
        launch_service_provider = launch_service_provider,
        mission = mission,
        name = name,
        net = net,
        net_precision = net_precision,
        pad = pad,
        status = status,
        url = url,
        window_end = window_end,
        window_start = window_start

    )


}
fun LaunchEntity.toLauchUpcomingLauch(): UpcomingLaunches {
    return UpcomingLaunches(
        id = id,
        image = image,
        last_updated = last_updated,
        launch_service_provider = launch_service_provider,
        mission = mission,
        name = name,
        net = net,
        net_precision = net_precision,
        pad = pad,
        status = status,
        window_end = window_end,
        window_start = window_start,
        url = url
    )
}
    fun UpcomingLaunches.toReminder():Reminder {
        return Reminder(
            id = id,
            image = image,
            last_updated = last_updated,
            launch_service_provider = launch_service_provider,
            mission = mission,
            name = name,
            net = net,
            net_precision = net_precision,
            pad = pad,
            status = status,
            window_end = window_end,
            window_start = window_start,
            url = url

        )
    }





