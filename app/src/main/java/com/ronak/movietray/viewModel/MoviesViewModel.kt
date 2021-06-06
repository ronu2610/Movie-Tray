package com.ronak.movietray.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronak.movietray.data.DataRepositorySource
import com.ronak.movietray.data.Resource
import com.ronak.movietray.dto.MovieItem
import com.ronak.movietray.dto.MoviesData
import com.ronak.movietray.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject
constructor(private val dataRepositoryRepository: DataRepositorySource) : ViewModel() {

    private val LANGUAGE = "en-US"
    private val moviesLiveDataPrivate = MutableLiveData<Resource<MoviesData>>()
    val moviesLiveData: LiveData<Resource<MoviesData>> get() = moviesLiveDataPrivate

    private val openMovieDetailsPrivate = MutableLiveData<SingleEvent<MovieItem>>()
    val openMovieDetails: LiveData<SingleEvent<MovieItem>> get() = openMovieDetailsPrivate

    private val movieItemLiveDataPrivate = MutableLiveData<MovieItem>()
    val movieItemLiveData: LiveData<MovieItem> get() = movieItemLiveDataPrivate

    fun getMovies() {
        viewModelScope.launch {
            moviesLiveDataPrivate.value = Resource.Loading()
            dataRepositoryRepository.fetchMovies(
                "popular",
                "c9da5821374b801b21a055d441e647b8",
                LANGUAGE,
                1
            ).collect {
                moviesLiveDataPrivate.value = it
            }
        }
    }

    fun openMovieDetails(movieItem: MovieItem) {
        movieItemLiveDataPrivate.value = movieItem
        openMovieDetailsPrivate.value = SingleEvent(movieItem)
    }

}