package com.example.spacelaunches.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey (key: RemoteKey)

   @Query("Delete from RemoteKey")
    suspend fun nukeTable()

   @Query("Select * from RemoteKey where id == :id ")
    suspend fun getRemoteKey(id:Int):RemoteKey

}