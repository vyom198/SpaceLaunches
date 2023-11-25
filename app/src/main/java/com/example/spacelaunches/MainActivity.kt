package com.example.spacelaunches

import  android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.spacelaunches.navigation.BottomNavGraph
import com.example.spacelaunches.navigation.BottomNavScreen
import com.example.spacelaunches.presentation.composable.launchList
import com.example.spacelaunches.ui.theme.SpaceLaunchesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceLaunchesTheme {
                // A surface container using the 'background' color from the theme
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
                        bottomBar = {
                            NavigationBar {
                                screens.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            navController.navigate(item.route)
                                        },
                                        label = {
                                            Text(text = item.name)
                                        },
                                        alwaysShowLabel = false,
                                        icon = {
                                            Icon(
                                                    imageVector = if (index == selectedItemIndex) {
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
                       BottomNavGraph(navController = navController, upcomingViewmodel = hiltViewModel(),
                           reminderViewmodel = hiltViewModel())
                    }
                }
                }
            }
        }
    }


