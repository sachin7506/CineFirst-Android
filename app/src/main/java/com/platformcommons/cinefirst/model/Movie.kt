package com.platformcommons.cinefirst.model



data class Movie(
    val id: Int,
    val title: String,
    val release_date: String,
    val poster_path: String
)

data class MovieResponse(
    val page: Int,
    val results: List<Movie>
)


