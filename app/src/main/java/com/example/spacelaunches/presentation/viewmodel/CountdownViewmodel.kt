package com.example.spacelaunches.presentation.viewmodel

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacelaunches.util.TimeFormat.timeFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit


class CountdownViewmodel : ViewModel() {

    private var countDownTimer: CountDownTimer? = null
    var netMillis by mutableStateOf(0L)
        private set

     var timeLeft = mutableStateOf(netMillis)
         private set
    val countDownInterval = 1000L

    var timerText = mutableStateOf(timeLeft.value.timeFormat())
        private set
    var isPlaying = mutableStateOf(false)
        private set

    fun startCountdown(netTime: String) {
        netMillis = getTimeDifferenceInMillis(netTime)
        timeLeft.value = netMillis
        isPlaying.value = true
        viewModelScope.launch {

            countDownTimer =  object : CountDownTimer(timeLeft.value, countDownInterval) {
                override fun onTick(currentTimeLeft: Long) {
                    timerText.value = currentTimeLeft.timeFormat()
                    timeLeft.value = currentTimeLeft
                }

                override fun onFinish() {

                    isPlaying.value = false
                }
            }.start()


        }


}

    fun getTimeDifferenceInMillis(targetTimeString: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val targetTime = dateFormat.parse(targetTimeString)
        // Calculate the time difference in milliseconds
        val timeDifferenceMillis = targetTime.time -  System.currentTimeMillis()
        return timeDifferenceMillis
    }

}