package com.example.spacelaunches.presentation.states.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.spacelaunches.util.toLauchUpcomingLauch
import com.example.spacelaunches.util.toReminder
import com.example.spacelaunches.data.repo.LaunchRepoIml
import com.example.spacelaunches.domain.model.UpcomingLaunches
import com.example.spacelaunches.domain.usecases.SetReminderUsecase
import com.example.spacelaunches.util.Constants
import com.example.spacelaunches.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Upcomingviewmodel @Inject constructor(
private val launchRepoIml: LaunchRepoIml,
    private val setReminderUsecase: SetReminderUsecase

):ViewModel() {

    private val _eventFlow: MutableSharedFlow<LaunchesScreenEvent> = MutableSharedFlow()
    val eventFlow = _eventFlow.asSharedFlow()


    val pager =
         launchRepoIml.getUpcomingLaunches().map {
          it.map {
             it.toLauchUpcomingLauch()
          }
      }.cachedIn(viewModelScope)

    fun setReminder (upcomingLaunches: UpcomingLaunches) {
        viewModelScope.launch(Dispatchers.IO) {
            val reminder = upcomingLaunches.toReminder()
            setReminderUsecase(reminder).let { result ->
                when (result) {
                    is Resource.Error -> {
                        when (result.message) {
                            Constants.REMINDER_PERMISSION_NOT_AVAILABLE -> {
                                _eventFlow.emit(
                                    LaunchesScreenEvent.PermissionToSetReminderNotGranted
                                )
                            }

                            Constants.NOTIFICATION_PERMISSION_NOT_AVAILABLE -> {
                                _eventFlow.emit(
                                    LaunchesScreenEvent.PermissionToSendNotificationsNotGranted
                                )
                            }

                            Constants.NOTIFICATION_REMINDER_PERMISSION_NOT_AVAILABLE -> {
                                _eventFlow.emit(
                                    LaunchesScreenEvent.PermissionsToSendNotificationsAndSetReminderNotGranted
                                )
                            }

                            else -> {
                                _eventFlow.emit(
                                    LaunchesScreenEvent.ReminderNotSet(result.message)
                                )
                            }
                        }
                    }

                    is Resource.Success -> {
                        _eventFlow.emit(LaunchesScreenEvent.ReminderSetSuccessfully)
                    }
                }
            }
        }
    }



    }



sealed class LaunchesScreenEvent(
    val infoMessage: String? = null
) {
    object ReminderSetSuccessfully : LaunchesScreenEvent()
    data class ReminderNotSet(val errorMessage: String?) : LaunchesScreenEvent(errorMessage)
    object PermissionToSetReminderNotGranted : LaunchesScreenEvent()
    object PermissionToSendNotificationsNotGranted : LaunchesScreenEvent()
    object PermissionsToSendNotificationsAndSetReminderNotGranted : LaunchesScreenEvent()
}


