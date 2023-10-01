package com.example.spacelaunches.presentation.composable

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spacelaunches.presentation.viewmodel.CountdownViewmodel


@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@Composable
fun CountDown (
    netTime: String,
    viewmodel: CountdownViewmodel = viewModel()
) {

    val days = viewmodel.days.toInt()
    val hours   =viewmodel.hours.toInt()
    val minutes   =viewmodel.minutes.toInt()
    val seconds   =viewmodel.seconds.toInt()
    val animatedDays by animateIntAsState(targetValue = days)
    val animatedHours by animateIntAsState(targetValue = hours)
    val animatedMinutes by animateIntAsState(targetValue = minutes)
    val animatedSeconds by animateIntAsState(targetValue = seconds)

    LaunchedEffect(seconds) {
        viewmodel.startCountdown(netTime)
    }

    Row(
        modifier = Modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.Center,

    ) {
        Text(
            text = "T- ${animatedDays}:",
            maxLines = 1,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold, fontSize = 18.sp
        )
        Text(
            text = "${animatedHours}:",
            maxLines = 1,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold, fontSize = 18.sp
        )
        Text(
            text = "${animatedMinutes}:",
            maxLines = 1,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold, fontSize = 18.sp
        )
        Text(
            text = animatedSeconds.toString(),
            maxLines = 1,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold, fontSize = 18.sp
        )
    }


}