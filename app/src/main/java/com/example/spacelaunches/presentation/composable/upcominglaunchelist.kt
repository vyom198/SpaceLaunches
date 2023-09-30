package com.example.spacelaunches.presentation.composable

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.example.spacelaunches.domain.model.UpcomingLaunches
import com.example.spacelaunches.presentation.viewmodel.Upcomingviewmodel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun launchList(
    viewModel: Upcomingviewmodel = hiltViewModel()
) {
    val pagingdata = viewModel.pager.collectAsLazyPagingItems()


    val context = LocalContext.current
    LaunchedEffect(key1 = pagingdata.loadState) {
        if (pagingdata.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (pagingdata.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(topBar = {




    }) {


        Box(modifier = Modifier.fillMaxSize()) {
            if (pagingdata.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {


                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(pagingdata.itemCount){
                        val launchItem = pagingdata[it]
                        if (launchItem != null) {
                            LaunchItem(lauches = launchItem, viewmodel = viewModel)
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



}