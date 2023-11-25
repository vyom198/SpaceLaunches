package com.example.spacelaunches.presentation.states.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.spacelaunches.data.local.entity.Reminder
import com.example.spacelaunches.data.local.toLauchUpcomingLauch
import com.example.spacelaunches.data.local.toReminder
import com.example.spacelaunches.data.repo.LaunchRepoIml
import com.example.spacelaunches.domain.model.UpcomingLaunches
import com.example.spacelaunches.domain.repostitory.LaunchRepo
import com.example.spacelaunches.domain.usecases.SetReminderUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Upcomingviewmodel @Inject constructor(
private val launchRepoIml: LaunchRepoIml,
    private val setReminderUsecase: SetReminderUsecase

):ViewModel() {

    val pager =
         launchRepoIml.getUpcomingLaunches().map {
          it.map {
             it.toLauchUpcomingLauch()
          }
      }.cachedIn(viewModelScope)

    fun setReminder (upcomingLaunches: UpcomingLaunches) {
        viewModelScope.launch {
            val reminder = upcomingLaunches.toReminder()
            setReminderUsecase(reminder)
        }
    }



    }






