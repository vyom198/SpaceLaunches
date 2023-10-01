package com.example.spacelaunches.data.model

data class Mission_dto(
    val agencies: List<Agency_dto>,
    val description: String,
    val id: Int,
    //val info_urls: List<Any>,
    val launch_designator: Any,
    val name: String,
    val orbit: Orbit_dto,
    val type: String,
   // val vid_urls: List<Any>
)