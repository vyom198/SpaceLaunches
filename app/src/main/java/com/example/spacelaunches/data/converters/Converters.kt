package com.example.spacelaunches.data.converters

import androidx.room.TypeConverter
import com.example.spacelaunches.data.model.Agency_dto
import com.example.spacelaunches.data.model.LaunchServiceProvider_dto
import com.example.spacelaunches.data.model.Location_dto
import com.example.spacelaunches.data.model.Mission_dto
import com.example.spacelaunches.data.model.NetPrecision_dto
import com.example.spacelaunches.data.model.Orbit_dto
import com.example.spacelaunches.data.model.Pad_dto
import com.example.spacelaunches.data.model.Status_dto
import com.google.gson.Gson

class Converters {
    private val gson = Gson()
    @TypeConverter
    fun fromLaunchServiceProviderDto(value: LaunchServiceProvider_dto?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toLaunchSerivceProviderDto(value: String): LaunchServiceProvider_dto? {
        return gson.fromJson(value, LaunchServiceProvider_dto::class.java)
    }

    @TypeConverter
    fun fromMission_dto(value: Mission_dto?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMission_dto(value: String): Mission_dto? {
        return gson.fromJson(value, Mission_dto::class.java)
    }

    @TypeConverter
    fun fromAgencydto(value: Agency_dto?):String{
        return gson.toJson(value)
    }

    @TypeConverter
    fun toAgency_dto (value: String): Agency_dto?{
        return gson.fromJson(value, Agency_dto::class.java)
    }

    @TypeConverter
    fun fromOrbit_dto(value:Orbit_dto?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toOrbit_dto (value: String): Orbit_dto?{
        return gson.fromJson(value,Orbit_dto::class.java)
    }

    @TypeConverter
    fun fromNetPrecision_dto(value:NetPrecision_dto?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toNetPrecision_dto (value: String):NetPrecision_dto? {
        return gson.fromJson(value,NetPrecision_dto::class.java)
    }



    @TypeConverter
    fun fromPad_dto(value:Pad_dto?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPad_dto (value: String):Pad_dto?{
        return gson.fromJson(value,Pad_dto::class.java)
    }
    @TypeConverter
    fun fromLocation_dto(value:Location_dto?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toLocation_dto(value: String):Location_dto?{
        return gson.fromJson(value,Location_dto::class.java)
    }
    @TypeConverter
    fun fromStatus_dto(value:Status_dto?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStatus_dto(value: String):Status_dto?{
        return gson.fromJson(value,Status_dto::class.java)
    }
}