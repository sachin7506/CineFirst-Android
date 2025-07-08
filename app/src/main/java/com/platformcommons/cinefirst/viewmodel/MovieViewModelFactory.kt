package com.platformcommons.cinefirst.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.platformcommons.cinefirst.remote.RetrofitMovieClient
import com.platformcommons.cinefirst.repository.MovieRepository

class MovieViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val movieApi = RetrofitMovieClient.apiService
        val repository = MovieRepository(movieApi)
        return MovieViewModel(repository) as T
    }
}
