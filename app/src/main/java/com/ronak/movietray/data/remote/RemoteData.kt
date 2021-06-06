package com.ronak.movietray.data.remote

import com.ronak.movietray.data.Resource
import com.ronak.movietray.data.remote.service.MoviesService
import com.ronak.movietray.dto.MoviesData
import com.ronak.movietray.dto.TrailerDto
import com.ronak.movietray.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteData @Inject
constructor(
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity
) : RemoteDataSource {
    override suspend fun fetchMovies(
        category: String,
        api_key: String,
        lang: String,
        page: Int
    ): Resource<MoviesData> {
        val recipesService = serviceGenerator.createService(MoviesService::class.java)
        return when (val response =
            processCall { recipesService.getMovies(category, api_key, lang, page) }) {
            is Any -> {
                Resource.Success(data = response as MoviesData)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun fetchTrailers(
        id: Int,
        api_key: String,
        lang: String,
        type: String
    ): Resource<TrailerDto> {
        val recipesService = serviceGenerator.createService(MoviesService::class.java)
        return when (val response =
            processCall { recipesService.getTrailers(id, api_key, lang, type) }) {
            is Any -> {
                Resource.Success(data = response as TrailerDto)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return -1
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            -2
        }
    }
}
