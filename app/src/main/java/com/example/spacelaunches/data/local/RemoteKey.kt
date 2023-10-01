package com.example.spacelaunches.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
   val prev : Int?,
   val next : Int?,

)
