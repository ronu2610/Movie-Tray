package com.ronak.movietray.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronak.movietray.data.DataRepositorySource
import com.ronak.movietray.data.Resource
import com.ronak.movietray.dto.MovieItem
import com.ronak.movietray.dto.TrailerDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieItemViewModel @Inject
constructor(private val dataRepositoryRepository: DataRepositorySource) : ViewModel() {

    private val LANGUAGE = "en-US"
    private val TYPE = "Trailer"
    private val movieItemLiveDataPrivate = MutableLiveData<MovieItem>()
    val movieItemLiveData: LiveData<MovieItem> get() = movieItemLiveDataPrivate

    private val trailerDtoLiveDataPrivate = MutableLiveData<Resource<TrailerDto>>()
    val trailerDtoLiveData: LiveData<Resource<TrailerDto>> get() = trailerDtoLiveDataPrivate

    fun initIntentData(movie: MovieItem) {
        movieItemLiveDataPrivate.value = movie
        fetchTrailers()
    }

    private fun fetchTrailers() {
        viewModelScope.launch {
            trailerDtoLiveDataPrivate.value = Resource.Loading()
            movieItemLiveDataPrivate.value?.let { it ->
                dataRepositoryRepository.fetchTrailers(
                    it.id,
                    "c9da5821374b801b21a055d441e647b8",
                    LANGUAGE,
                    TYPE
                ).collect {
                    trailerDtoLiveDataPrivate.value = it
                }
            }
        }
    }
}