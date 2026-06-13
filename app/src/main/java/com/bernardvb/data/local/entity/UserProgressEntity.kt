package com.bernardvb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val userId: String,
    val totalXP: Int = 0,
    val currentLevel: String = "CURIOUS_MIND",
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastThinkingDay: Long = 0L,
    val graceUsedThisWeek: Boolean = false,
    val streakShieldsRemaining: Int = 0,
    val totalAnalyses: Int = 0,
    val totalJournalEntries: Int = 0,
    val totalChallengesCompleted: Int = 0,
    val decisionAccuracyScore: Float? = null,
    val badges: String = "[]",        // JSON array
    val goalsSelected: String = "[]", // JSON array
    val updatedAt: Long = System.currentTimeMillis()
)
