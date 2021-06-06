package com.ronak.movietray.ui.details

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.ronak.movietray.MOVIE_ITEM_KEY
import com.ronak.movietray.R
import com.ronak.movietray.data.Resource
import com.ronak.movietray.databinding.ActivityDetailsBinding
import com.ronak.movietray.dto.GenresRepository
import com.ronak.movietray.dto.MovieItem
import com.ronak.movietray.dto.TrailerDto
import com.ronak.movietray.dto.TrailerItem
import com.ronak.movietray.ui.details.adapter.GenresAdapter
import com.ronak.movietray.utils.observe
import com.ronak.movietray.viewModel.MovieItemViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val moviesViewModel: MovieItemViewModel by viewModels()
    private lateinit var binding: ActivityDetailsBinding
    private val genres = GenresRepository.getAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movieItem = intent.extras?.get(MOVIE_ITEM_KEY) as MovieItem?
        movieItem?.let { moviesViewModel.initIntentData(it) }
        observeViewModel()
    }

    private fun observeViewModel() {
        observe(moviesViewModel.movieItemLiveData, ::bindListData)
        observe(moviesViewModel.trailerDtoLiveData, ::handleTrailersList)
    }

    private fun handleTrailersList(status: Resource<TrailerDto>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindTrailerListData(it) }
            is Resource.DataError -> {
                showDataView(false)
            }
        }
    }

    private fun bindTrailerListData(trailerDto: TrailerDto) {
        binding.ivPoster.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + trailerDto.results[0].key)
                )
            )
        }
    }

    private fun showDataView(show: Boolean) {

    }

    private fun showLoadingView() {
    }

    private fun bindListData(movieItem: MovieItem) {
        binding.movie = movieItem
        Picasso.get().load("https://image.tmdb.org/t/p/w342" + movieItem.poster_path)
            .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
            .into(binding.ivPoster)

        val progress: Drawable = binding.rating.progressDrawable
        DrawableCompat.setTint(progress, Color.WHITE)

        binding.rating.rating = (((movieItem.vote_average / 2.0).toFloat()) + 1f)


        val layoutManager = FlexboxLayoutManager()
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.alignItems = AlignItems.BASELINE
        layoutManager.justifyContent = JustifyContent.FLEX_START
        binding.rvGenres.layoutManager = layoutManager

        val genresAdapter = GenresAdapter(getGenreName(movieItem.genre_ids))
        binding.rvGenres.adapter = genresAdapter
    }

    private fun getGenreName(genreIds: List<Int>): MutableList<String> {
        val givenList = mutableListOf<String>()

        for (genreId in genreIds) {
            for (genre in genres) {
                if (genreId == genre.id) {
                    givenList.add(genre.name)
                    break
                }
            }
        }
        return givenList
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }

    fun shareContent(item: MenuItem?) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        var messageContent = getString(R.string.messageShare)
        val trailers: List<TrailerItem>? = moviesViewModel.trailerDtoLiveData.value?.data?.results
        if (!trailers.isNullOrEmpty()) {
            messageContent += "http://www.youtube.com/watch?v=" + trailers[0].key
        } else {
            moviesViewModel.movieItemLiveData.value.let {
                messageContent += it!!.title.toUpperCase(
                    Locale.getDefault()
                ) + "\n" + it.overview
            }
        }
        sendIntent.putExtra(Intent.EXTRA_TEXT, messageContent)
        sendIntent.type = "text/plain"
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}