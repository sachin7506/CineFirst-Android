package com.platformcommons.cinefirst.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.platformcommons.cinefirst.model.User
import com.platformcommons.cinefirst.remote.RetrofitClient
import com.platformcommons.cinefirst.repository.UserRepository
import com.platformcommons.cinefirst.workers.NetworkUtils
import kotlinx.coroutines.flow.*

class UserListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(RetrofitClient.api)
    private val networkUtils = NetworkUtils(application)

    val pagedUsers: Flow<PagingData<User>> = repository
        .getPagedUsers()
        .cachedIn(viewModelScope)


    val isConnected: StateFlow<Boolean> = flow {
        emit(networkUtils.isInternetAvailable())
        emitAll(networkUtils.observeNetworkStatus())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
}
