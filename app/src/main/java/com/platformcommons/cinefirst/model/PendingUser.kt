package com.platformcommons.cinefirst.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_users")
data class PendingUser(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val job: String
)
