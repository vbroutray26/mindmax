package com.bernardvb.data.repository

import com.bernardvb.data.local.dao.UserProgressDao
import com.bernardvb.data.local.entity.UserProgressEntity
import com.bernardvb.domain.model.ThinkerLevel
import com.bernardvb.domain.model.UserProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProgressRepositoryImpl @Inject constructor(
    private val dao: UserProgressDao
) : UserProgressRepository {

    override fun getProgress(userId: String): Flow<UserProgress?> =
        dao.getProgress(userId).map { it?.toDomain() }

    override suspend fun addXP(userId: String, xp: Int) {
        dao.addXP(userId, xp)
    }

    override suspend fun updateStreak(userId: String, streak: Int) {
        dao.updateStreak(userId, streak, System.currentTimeMillis())
    }

    override suspend fun initProgress(userId: String) {
        if (dao.getProgressOnce(userId) == null) {
            dao.upsertProgress(UserProgressEntity(userId = userId))
        }
    }

    private fun UserProgressEntity.toDomain(): UserProgress {
        val xp = totalXP
        return UserProgress(
            userId = userId,
            totalXP = xp,
            currentLevel = ThinkerLevel.forXP(xp),
            currentStreak = currentStreak,
            longestStreak = longestStreak,
            lastThinkingDay = lastThinkingDay,
            graceUsedThisWeek = graceUsedThisWeek,
            streakShieldsRemaining = streakShieldsRemaining,
            totalAnalyses = totalAnalyses,
            totalJournalEntries = totalJournalEntries,
            totalChallengesCompleted = totalChallengesCompleted,
            decisionAccuracyScore = decisionAccuracyScore,
            badges = emptyList(),
            goalsSelected = emptyList(),
            updatedAt = updatedAt
        )
    }
}
