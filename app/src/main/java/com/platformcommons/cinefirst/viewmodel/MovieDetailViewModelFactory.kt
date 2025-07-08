package com.platformcommons.cinefirst.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.platformcommons.cinefirst.remote.RetrofitMovieClient
import com.platformcommons.cinefirst.repository.MovieDetailRepository

class MovieDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = RetrofitMovieClient.apiService
        val repository = MovieDetailRepository(apiService)
        return MovieDetailViewModel(repository) as T
    }
}
