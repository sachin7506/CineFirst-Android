package com.platformcommons.cinefirst.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PendingUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: PendingUser)

    @Query("SELECT * FROM pending_users")
    suspend fun getAll(): List<PendingUser>

    @Delete
    suspend fun delete(user: PendingUser)
}
