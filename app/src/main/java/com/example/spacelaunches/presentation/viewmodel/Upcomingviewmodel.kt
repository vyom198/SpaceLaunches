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

    val pager =
         launchRepo.getUpcomingLaunches().map {
          it.map {
             it.toLauchUpcomingLauch()
          }
      }.cachedIn(viewModelScope)


    }






