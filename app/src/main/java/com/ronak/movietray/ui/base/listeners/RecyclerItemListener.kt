package com.ronak.movietray.ui.base.listeners

import com.ronak.movietray.dto.MovieItem

interface RecyclerItemListener {
    fun onItemSelected(movieItem : MovieItem)
}
