package com.ronak.movietray.data

import com.ronak.movietray.dto.MoviesData
import com.ronak.movietray.dto.TrailerDto
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun fetchMovies(
        category: String,
        api_key: String,
        lang: String,
        page: Int
    ): Flow<Resource<MoviesData>>

    suspend fun fetchTrailers(
        id: Int,
        api_key: String,
        lang: String,
        type: String
    ): Flow<Resource<TrailerDto>>

}
