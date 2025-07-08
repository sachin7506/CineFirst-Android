package com.platformcommons.cinefirst.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.platformcommons.cinefirst.model.Movie
import com.platformcommons.cinefirst.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieViewModel(repository: MovieRepository) : ViewModel() {
    val movies: Flow<PagingData<Movie>> = repository.getTrendingMovies().cachedIn(viewModelScope)
}
