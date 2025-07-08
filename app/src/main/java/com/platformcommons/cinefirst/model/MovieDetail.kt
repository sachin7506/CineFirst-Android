package com.platformcommons.cinefirst.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String?
)
