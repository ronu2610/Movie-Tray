package com.ronak.movietray.data

import com.ronak.movietray.data.remote.RemoteData
import com.ronak.movietray.dto.MoviesData
import com.ronak.movietray.dto.TrailerDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(
    private val remoteRepository: RemoteData,
    private val ioDispatcher: CoroutineContext
) : DataRepositorySource {

    override suspend fun fetchMovies(
        category: String,
        api_key: String,
        lang: String,
        page: Int
    ): Flow<Resource<MoviesData>> {
        return flow {
            emit(remoteRepository.fetchMovies(category, api_key, lang, page))
        }.flowOn(ioDispatcher)
    }

    override suspend fun fetchTrailers(
        id: Int,
        api_key: String,
        lang: String,
        type: String
    ): Flow<Resource<TrailerDto>> {
        return flow {
            emit(remoteRepository.fetchTrailers(id, api_key, lang, type))
        }.flowOn(ioDispatcher)
    }


}
