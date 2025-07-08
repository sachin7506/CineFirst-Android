package com.platformcommons.cinefirst.repository


import com.platformcommons.cinefirst.model.MovieDetail
import com.platformcommons.cinefirst.remote.MovieApiService
import com.platformcommons.cinefirst.BuildConfig

class MovieDetailRepository(private val apiService: MovieApiService) {
    suspend fun getMovieDetail(movieId: Int): MovieDetail {
        return apiService.getMovieDetail(movieId, BuildConfig.TMDB_API_KEY)
    }
}
