package com.ronak.movietray.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesData(
    val page: Int,
    val results: List<MovieItem>,
    val total_pages: Int,
    val total_results: Int
) {
    override fun toString(): String {
        return "MoviesData(page=$page, results=$results, total_pages=$total_pages, total_results=$total_results)"
    }
}