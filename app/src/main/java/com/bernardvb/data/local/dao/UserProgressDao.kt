package com.bernardvb.data.local.dao

import androidx.room.*
import com.bernardvb.data.local.entity.UserProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProgressDao {

    @Query("SELECT * FROM user_progress WHERE userId = :userId")
    fun getProgress(userId: String): Flow<UserProgressEntity?>

    @Query("SELECT * FROM user_progress WHERE userId = :userId")
    suspend fun getProgressOnce(userId: String): UserProgressEntity?

    @Upsert
    suspend fun upsertProgress(progress: UserProgressEntity)

    @Query("UPDATE user_progress SET totalXP = totalXP + :xp, updatedAt = :now WHERE userId = :userId")
    suspend fun addXP(userId: String, xp: Int, now: Long = System.currentTimeMillis())

    @Query("UPDATE user_progress SET totalAnalyses = totalAnalyses + 1, updatedAt = :now WHERE userId = :userId")
    suspend fun incrementAnalyses(userId: String, now: Long = System.currentTimeMillis())

    @Query("UPDATE user_progress SET currentStreak = :streak, longestStreak = MAX(longestStreak, :streak), lastThinkingDay = :day, updatedAt = :now WHERE userId = :userId")
    suspend fun updateStreak(userId: String, streak: Int, day: Long, now: Long = System.currentTimeMillis())
}
