package com.platformcommons.cinefirst.repository

import com.platformcommons.cinefirst.model.AddUserRequest
import com.platformcommons.cinefirst.model.PendingUser
import com.platformcommons.cinefirst.model.PendingUserDao
import com.platformcommons.cinefirst.remote.ApiService

class AddUserRepository(
    private val apiService: ApiService,
    private val dao: PendingUserDao
) {
    suspend fun submitUserOnline(request: AddUserRequest) = apiService.addUser(request)
    suspend fun saveUserOffline(user: PendingUser) = dao.insert(user)
}
