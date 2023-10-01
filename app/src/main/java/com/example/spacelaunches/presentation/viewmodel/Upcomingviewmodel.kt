package com.example.spacelaunches.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.map
import com.example.spacelaunches.data.local.toLauchUpcomingLauch
import com.example.spacelaunches.data.repo.LaunchRepo
import com.example.spacelaunches.domain.model.UpcomingLaunches
import com.example.spacelaunches.presentation.states.LaunchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject
@HiltViewModel
class Upcomingviewmodel @Inject constructor(
private val launchRepo: LaunchRepo
):ViewModel() {
    private val _timeRemaining = MutableStateFlow(0L)
    private val _days = MutableStateFlow(0L)
    val days: StateFlow<Long> = _days

    private val _hours =  MutableStateFlow(0L)
    val hours: StateFlow<Long> = _hours

    private val _minutes =  MutableStateFlow(0L)
    val minutes: StateFlow<Long> = _minutes

    private val _seconds =  MutableStateFlow(0L)
    val seconds: StateFlow<Long> = _seconds
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
     val pager =
         launchRepo.getUpcomingLaunches().map {
          it.map {
             it.toLauchUpcomingLauch()
          }
      }.cachedIn(viewModelScope)

    fun startCountdown(netTime: String) {
        val netDate = dateFormat.parse(netTime)
        val netMillis = netDate?.time ?: 0L

        viewModelScope.launch {
            _timeRemaining.value = maxOf(netMillis - System.currentTimeMillis(), 0L)

             _days.value = TimeUnit.MILLISECONDS.toDays(_timeRemaining.value)
            _timeRemaining.value -= TimeUnit.DAYS.toMillis(_days.value)
            _hours.value = TimeUnit.MILLISECONDS.toHours(_timeRemaining.value)
            _timeRemaining.value -= TimeUnit.HOURS.toMillis(_hours.value)
            _minutes.value = TimeUnit.MILLISECONDS.toMinutes(_timeRemaining.value)
            _timeRemaining.value -= TimeUnit.MINUTES.toMillis(_minutes.value)
            _seconds.value = TimeUnit.MILLISECONDS.toSeconds(_timeRemaining.value)

        }
    }




}

