package com.example.spacelaunches.presentation.composable

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable


import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spacelaunches.presentation.viewmodel.CountdownViewmodel
import com.example.spacelaunches.presentation.viewmodel.Upcomingviewmodel
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@Composable
fun CountDown (
    netTime: String,
    countdownViewmodel: CountdownViewmodel = viewModel()
) {

    LaunchedEffect(Unit) {
        countdownViewmodel.startCountdown(netTime)
    }

    Row(
        modifier = Modifier.wrapContentWidth()
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("T- ${countdownViewmodel.timerText.value} ")
                }


            },
            modifier = Modifier.padding(8.dp),
            fontSize = 18.sp
        )
    }


}