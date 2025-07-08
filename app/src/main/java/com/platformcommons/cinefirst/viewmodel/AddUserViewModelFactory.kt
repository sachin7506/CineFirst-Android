package com.platformcommons.cinefirst.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.platformcommons.cinefirst.model.AppDatabase
import com.platformcommons.cinefirst.remote.RetrofitClient
import com.platformcommons.cinefirst.repository.AddUserRepository

class AddUserViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = AppDatabase.getDatabase(context).pendingUserDao()
        val api = RetrofitClient.api
        val repository = AddUserRepository(api, dao)
        return AddUserViewModel(repository) as T
    }
}
