package com.ronak.movietray.data.remote.service

import com.ronak.movietray.dto.MoviesData
import com.ronak.movietray.dto.ReviewDTO
import com.ronak.movietray.dto.TrailerDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("/3/movie/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("api_key") api_key: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Response<MoviesData>


    @GET("/3/movie/{id}/videos")
    suspend fun getTrailers(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("type") type: String?
    ): Response<TrailerDto>

    @GET("/3/movie/{id}/reviews")
    suspend fun getReviews(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("type") type: String?
    ): Response<ReviewDTO>
}
