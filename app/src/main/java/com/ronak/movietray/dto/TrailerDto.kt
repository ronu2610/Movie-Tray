package com.ronak.movietray.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrailerDto(
    val id: Int,
    val results: List<TrailerItem>
)

data class TrailerItem(
    val id : String,
    val iso_639_1 : String,
    val iso_3166_1 : String,
    val key : String,
    val name : String,
    val site : String,
    val size : Int,
    val type : String
)