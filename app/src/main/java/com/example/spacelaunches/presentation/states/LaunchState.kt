package com.example.spacelaunches.presentation.states

import com.example.spacelaunches.domain.model.UpcomingLaunches

data class LaunchState(
    val isLoading : Boolean = false,
    val data :UpcomingLaunches? = null,
    val error: String? = null
)
