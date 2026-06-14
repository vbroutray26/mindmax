package com.bernardvb.data.repository

import com.bernardvb.data.local.dao.UserProgressDao
import com.bernardvb.data.local.entity.UserProgressEntity
import com.bernardvb.domain.model.ThinkerLevel
import com.bernardvb.domain.model.UserProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject
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
            domainBreakdown = parseDomainBreakdown(domainBreakdown),
            topModels = parseJsonStringList(topModels),
            updatedAt = updatedAt
        )
    }

    private fun parseDomainBreakdown(json: String): Map<String, Int> = try {
        val obj = JSONObject(json)
        obj.keys().asSequence().associateWith { obj.getInt(it) }
    } catch (_: Exception) { emptyMap() }

    private fun parseJsonStringList(json: String): List<String> = try {
        val arr = JSONArray(json)
        (0 until arr.length()).map { arr.getString(it) }
    } catch (_: Exception) { emptyList() }
}
