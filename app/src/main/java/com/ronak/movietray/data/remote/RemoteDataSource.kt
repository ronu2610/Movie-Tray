package com.ronak.movietray.data.remote

import com.ronak.movietray.data.Resource
import com.ronak.movietray.dto.MoviesData
import com.ronak.movietray.dto.TrailerDto

internal interface RemoteDataSource {
    suspend fun fetchMovies(
        category: String,
        api_key: String,
        lang: String,
        page: Int
    ): Resource<MoviesData>

    suspend fun fetchTrailers(
        id: Int,
        api_key: String,
        lang: String,
        type: String
    ): Resource<TrailerDto>
}
