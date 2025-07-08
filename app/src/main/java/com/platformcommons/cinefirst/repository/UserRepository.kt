package com.platformcommons.cinefirst.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.platformcommons.cinefirst.model.User
import com.platformcommons.cinefirst.paging.UserPagingSource
import com.platformcommons.cinefirst.remote.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository(private val apiService: ApiService) {
    fun getPagedUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 6),
            pagingSourceFactory = { UserPagingSource(apiService) }
        ).flow
    }
}
