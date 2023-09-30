package com.example.spacelaunches.data.model

data class Program_dto(
    val agencies: List<AgencyX_dto>,
    val description: String,
    val end_date: Any,
    val id: Int,
    val image_url: String,
    val info_url: String,
    val mission_patches: List<Any>,
    val name: String,
    val start_date: String,
    val url: String,
    val wiki_url: String
)