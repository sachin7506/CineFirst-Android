package com.platformcommons.cinefirst.model


data class AddUserResponse(
    val id: String,
    val name: String,
    val job: String,
    val createdAt: String
)

data class AddUserRequest(
    val name: String,
    val job: String
)

