package com.ronak.movietray.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ronak.movietray.databinding.LayoutGenreItemBinding

class GenresAdapter(private val genres: MutableList<String>) : RecyclerView.Adapter<GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val itemBinding = LayoutGenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int {
        return genres.size
    }
}

