package com.example.spacelaunches.util

import java.util.concurrent.TimeUnit



    object TimeFormat {
        private const val FORMAT = "%02d:%02d:%02d:%02d"

        fun Long.timeFormat(): String = String.format(
            FORMAT,
            TimeUnit.MILLISECONDS.toDays(this),
            TimeUnit.MILLISECONDS.toHours(this),
            TimeUnit.MILLISECONDS.toMinutes(this) % 60,
            TimeUnit.MILLISECONDS.toSeconds(this) % 60
        )

    }

