package com.example.spacelaunches.presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spacelaunches.presentation.viewmodel.Upcomingviewmodel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.CountDownLatch

@Composable
fun CountDown (
    onFinish: () -> Unit,
    netTime: String,
    viewModel: Upcomingviewmodel
) {
    LaunchedEffect(true) {
        viewModel.startCountdown(netTime)
    }
    Row(
        modifier = Modifier.wrapContentWidth()
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("T-  ")
                }
                append("${viewModel.days.value} : ")
                append("${viewModel.hours.value} : ")
                append("${viewModel.minutes.value} : ")
                append("${viewModel.seconds.value} ")
            },
            modifier = Modifier.padding(8.dp),
            fontSize = 18.sp
        )
    }


}