package com.ronak.movietray.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewDTO(
    val id : Int,
    val page : Int,
    val results : List<Results>,
    val total_pages : Int,
    val total_results : Int
)

data class AuthorDetails (
    val name : String,
    val username : String,
    val avatar_path : String,
    val rating : Int
)


data class Results (
    val author : String,
    val author_details : AuthorDetails,
    val content : String,
    val created_at : String,
    val id : String,
    val updated_at : String,
    val url : String
)