package com.example.spacelaunches.app

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spacelaunches.R
import com.example.spacelaunches.navigation.BottomNavScreen
import com.example.spacelaunches.presentation.composable.launchList
import com.example.spacelaunches.presentation.composable.reminderScreen
import com.example.spacelaunches.ui.theme.SpaceLaunchesTheme
import com.example.spacelaunches.util.Helpers.Companion.navigateSingleTopTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceLaunchesTheme {
                // A surface container using the 'background' color from the theme

                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()
                val screens = listOf(
                    BottomNavScreen.Home,
                    BottomNavScreen.Reminder
                )
                val navController = rememberNavController()
                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(

                        snackbarHost = {
                            SnackbarHost(snackbarHostState)
                        },
                        bottomBar = {
                            NavigationBar {
                                screens.forEach {  item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == item.id,
                                        onClick = {
                                            selectedItemIndex = item.id
                                            navController.navigateSingleTopTo(item.route)
                                        },
                                        label = {
                                            Text(text = item.name)
                                        },
                                        alwaysShowLabel = false,
                                        icon = {
                                            Icon(
                                                    imageVector = if (item.id == selectedItemIndex) {
                                                        item.selectedIcon
                                                    } else item.unselectedIcon,
                                                    contentDescription = item.name
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    ) {
                        val activity = (LocalContext.current as? Activity)
                        val actionLabel by rememberUpdatedState(
                            newValue = stringResource(id = R.string.reminders)
                        )
                        NavHost(navController = navController,   modifier =Modifier.padding(it),
                            startDestination = BottomNavScreen.Home.route){
                            composable(BottomNavScreen.Home.route,){
                                launchList( viewModel = hiltViewModel(),
                                    reminderSetSuccessfully = {
                                      scope.launch {
                                          val actionTaken = snackbarHostState.showSnackbar(
                                              it,
                                              actionLabel = actionLabel,
                                              withDismissAction = true,
                                              duration = SnackbarDuration.Short
                                          )
                                          when (actionTaken) {
                                              SnackbarResult.ActionPerformed -> {
                                               navController.navigateSingleTopTo(BottomNavScreen.Reminder.route)
                                                  selectedItemIndex = BottomNavScreen.Reminder.id
                                              }

                                              else -> {}
                                          }
                                      }
                                    },
                                    systemBootClicked = {
                                          activity?.finish()
                                    },
                                    reminderNotSet = {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                it,
                                                withDismissAction = true
                                            )
                                        }
                                    })
                            }
                            composable(BottomNavScreen.Reminder.route){

                                val snackBarMessage by rememberUpdatedState(
                                    newValue = stringResource(R.string.reminder_cancelled_successfully)
                                )
                                reminderScreen(viewModel = hiltViewModel(),
                                    onBackPressed = {
                                        navController.navigateSingleTopTo(BottomNavScreen.Home.route)
                                        selectedItemIndex = BottomNavScreen.Home.id
                                    }
                                , reminderCancelled = {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                snackBarMessage,
                                                withDismissAction = true
                                            )
                                        }
                                    },
                                    reminderNotCancelled = {message->
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message.toString(),
                                                withDismissAction = true
                                            )
                                        }

                                    }
                                )
                            }
                        }
                    }

                   }
                }
            }
        }
    }


