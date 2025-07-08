package com.platformcommons.cinefirst.viewmodel

import androidx.lifecycle.ViewModel
import com.platformcommons.cinefirst.model.MovieDetail
import com.platformcommons.cinefirst.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers

class MovieDetailViewModel(
    private val repository: MovieDetailRepository
) : ViewModel() {

    fun getMovieDetail(movieId: Int): Flow<Result<MovieDetail>> = flow {
        try {
            val detail = repository.getMovieDetail(movieId)
            emit(Result.success(detail))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
