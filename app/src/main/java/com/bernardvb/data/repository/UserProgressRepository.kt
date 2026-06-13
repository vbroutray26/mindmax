package com.bernardvb.data.repository

import com.bernardvb.domain.model.UserProgress
import kotlinx.coroutines.flow.Flow

interface UserProgressRepository {
    fun getProgress(userId: String): Flow<UserProgress?>
    suspend fun addXP(userId: String, xp: Int)
    suspend fun updateStreak(userId: String, streak: Int)
    suspend fun initProgress(userId: String)
}
