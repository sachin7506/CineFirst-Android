package com.platformcommons.cinefirst.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.platformcommons.cinefirst.model.Movie
import com.platformcommons.cinefirst.paging.MoviePagingSource
import com.platformcommons.cinefirst.remote.MovieApiService
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val apiService: MovieApiService) {
    fun getTrendingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MoviePagingSource(apiService) }
        ).flow
    }
}
