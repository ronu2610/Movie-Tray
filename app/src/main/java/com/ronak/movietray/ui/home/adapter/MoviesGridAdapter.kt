package com.ronak.movietray.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ronak.movietray.R
import com.ronak.movietray.databinding.LayoutVerticalMovieItemBinding
import com.ronak.movietray.dto.MovieItem
import com.ronak.movietray.ui.base.listeners.RecyclerItemListener
import com.ronak.movietray.viewModel.MoviesViewModel
import com.squareup.picasso.Picasso

class MoviesGridAdapter(
    private val moviesViewModel: MoviesViewModel,
    private val movies: List<MovieItem>
) : RecyclerView.Adapter<MoviesGridAdapter.MovieViewHolder>() {

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(movie: MovieItem) {
            moviesViewModel.openMovieDetails(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = LayoutVerticalMovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieViewHolder(private val itemBinding: LayoutVerticalMovieItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(movieItem: MovieItem, recyclerItemListener: RecyclerItemListener) {
            Picasso.get().load("https://image.tmdb.org/t/p/w342" + movieItem.poster_path)
                .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                .into(itemBinding.ivMovie)
            itemBinding.ivMovie.setOnClickListener {
                recyclerItemListener.onItemSelected(
                    movieItem
                )
            }
            itemBinding.ivMovie.scaleType = ImageView.ScaleType.FIT_XY
        }
    }
}

