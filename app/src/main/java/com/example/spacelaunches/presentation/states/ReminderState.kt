package com.example.spacelaunches.presentation.states

import com.example.spacelaunches.data.local.entity.Reminder

data class ReminderState(
    val data : List<Reminder> = emptyList(),
    val isLoading : Boolean = false,
    val error : String? = null
)
