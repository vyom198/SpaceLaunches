package com.example.spacelaunches.presentation.composable

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.spacelaunches.R
import com.example.spacelaunches.domain.model.UpcomingLaunches
import com.example.spacelaunches.presentation.states.viewmodel.LaunchesScreenEvent
import com.example.spacelaunches.presentation.states.viewmodel.Upcomingviewmodel
import com.example.spacelaunches.util.Constants
import com.example.spacelaunches.util.Helpers.Companion.openAppSettings
import com.example.spacelaunches.util.ReminderPermissionContract

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun launchList(
    viewModel: Upcomingviewmodel,
    systemBootClicked: () -> Unit,
    reminderSetSuccessfully: (String) -> Unit,
    reminderNotSet: (String) -> Unit
) {


    val pagingdata = viewModel.pager.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var launch: UpcomingLaunches? by remember {
        mutableStateOf(null)
    }
    val context: Context = LocalContext.current

    var showReminderPermissionRationale: Boolean by remember {
        mutableStateOf(false)
    }

    var showNotificationPermissionRationale: Boolean by remember {
        mutableStateOf(false)
    }

    var isNotificationPermissionDeclined: Boolean by remember {
        mutableStateOf(false)
    }

    val scheduleExactAlarmPermissionLauncher = rememberLauncherForActivityResult(
        contract = ReminderPermissionContract(),
        onResult = {isGranted->
            if (isGranted){
                launch?.let {
                    viewModel.setReminder(it)
                }
            }

        }
    )
    val postNotificationsPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                launch?.let {
                    viewModel.setReminder(it)
                }
            } else {
                isNotificationPermissionDeclined = true
            }
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is LaunchesScreenEvent.ReminderSetSuccessfully -> {
                    reminderSetSuccessfully(Constants.REMINDER_SET)
                }

                is LaunchesScreenEvent.ReminderNotSet -> {
                    reminderNotSet(event.infoMessage ?: Constants.REMINDER_NOT_SET)
                }

                is LaunchesScreenEvent.PermissionToSetReminderNotGranted -> {
                    showReminderPermissionRationale = true
                }

                LaunchesScreenEvent.PermissionToSendNotificationsNotGranted -> {
                    showNotificationPermissionRationale = true
                }

                LaunchesScreenEvent.PermissionsToSendNotificationsAndSetReminderNotGranted -> {
                    showReminderPermissionRationale = true
                    showNotificationPermissionRationale = true
                }
            }
        }
    }



    LaunchedEffect(key1 = pagingdata.loadState) {
        if (pagingdata.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (pagingdata.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection) ,

        topBar = {
            TopAppBar(
                 title = {
                         Text(text = "Space Launches", fontWeight = FontWeight.Medium)
                 },
                 scrollBehavior = scrollBehavior
                 
             )
 }
) {


        Box(modifier = Modifier.fillMaxSize()) {
            if (pagingdata.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 60.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    items(pagingdata.itemCount){
                        val launchItem = pagingdata[it]
                        if (launchItem != null) {
                            LaunchItem(launches = launchItem,
                                addReminderClicked =  {
                                 launch =it
                                 viewModel.setReminder(it)
                                })
                        }
                    }

                    item {
                        if (pagingdata.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }

    }
    BackHandler {
        systemBootClicked()
    }
    if (showReminderPermissionRationale) {
        ShowPermissionRationaleDialog(
            title = R.string.reminder_permission_required,
            content = R.string.alarm_permission_rationale,
            onDismissClick = {
                showReminderPermissionRationale = false
            },
            onConfirmClick = {
                showReminderPermissionRationale = false
                scheduleExactAlarmPermissionLauncher.launch(
                    Manifest.permission.SCHEDULE_EXACT_ALARM
                )
            },
            modifier = Modifier
        )
    }

    if (showNotificationPermissionRationale) {
        ShowPermissionRationaleDialog(
            title = R.string.notification_permission_required,
            content = R.string.notification_permission_rationale,
            onDismissClick = {
                showNotificationPermissionRationale = false
            },
            onConfirmClick = {
                showNotificationPermissionRationale = false
                if (!isNotificationPermissionDeclined) {
                    postNotificationsPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                } else {
                    (context as Activity).openAppSettings()
                }
            }
        )
    }

    if (isNotificationPermissionDeclined) {
        ShowPermissionRationaleDialog(
            title = R.string.notification_permission_required,
            content = R.string.notification_permission_mandatory_rationale,
            onDismissClick = {
            },
            onConfirmClick = {
                (context as Activity).openAppSettings()
            }
        )
    }
}

@Composable
fun ShowPermissionRationaleDialog(
    @StringRes
    title: Int,
    @StringRes
    content: Int,
    modifier: Modifier = Modifier,
    onDismissClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    AlertDialog(
        onDismissClick = onDismissClick,
        onConfirmClick = onConfirmClick,
        title = title,
        content = content,
        modifier = modifier
    )
}

