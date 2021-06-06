package com.ronak.movietray.dto

data class Genres(
    val id: Int,
    val name: String
)

object GenresRepository {
    fun getAll(): List<Genres> {
        return listOf(
            Genres(
                28,
                "Action"
            ),
            Genres(
                12,
                "Adventure"
            ),
            Genres(
                16,
                "Animation"
            ),
            Genres(
                35,
                "Comedy"
            ),
            Genres(
                80,
                "Crime"
            ),
            Genres(
                99,
                "Documentary"
            ),
            Genres(
                18,
                "Drama"
            ),
            Genres(
                10751,
                "Family"
            ),
            Genres(
                14,
                "Fantasy"
            ),
            Genres(
                36,
                "History"
            ),
            Genres(
                27,
                "Horror"
            ),
            Genres(
                10402,
                "Music"
            ),
            Genres(
                9648,
                "Mystery"
            ),
            Genres(
                10749,
                "Romance"
            ),
            Genres(
                878,
                "Science Fiction"
            ),
            Genres(
                10770,
                "TV Movie"
            ),
            Genres(
                53,
                "Thriller"
            ),
            Genres(
                10752,
                "War"
            ),
            Genres(
                37,
                "Western"
            )
        )
    }
}
