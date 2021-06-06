package com.ronak.movietray.ui.details.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ronak.movietray.databinding.LayoutGenreItemBinding
import com.ronak.movietray.databinding.LayoutHorizontalMovieItemBinding
import com.ronak.movietray.dto.Genres

class GenreViewHolder(private val itemBinding: LayoutGenreItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(genre: String) {
        itemBinding.tvGenre.text = genre
    }
}

