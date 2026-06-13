package com.bernardvb.domain.model

data class UserProgress(
    val userId: String,
    val totalXP: Int,
    val currentLevel: ThinkerLevel,
    val currentStreak: Int,
    val longestStreak: Int,
    val lastThinkingDay: Long,
    val graceUsedThisWeek: Boolean,
    val streakShieldsRemaining: Int,
    val totalAnalyses: Int,
    val totalJournalEntries: Int,
    val totalChallengesCompleted: Int,
    val decisionAccuracyScore: Float?,
    val badges: List<String>,
    val goalsSelected: List<String>,
    val updatedAt: Long
)

enum class ThinkerLevel(val displayName: String, val xpRequired: Int) {
    CURIOUS_MIND("Curious Mind", 0),
    SHARP_ANALYST("Sharp Analyst", 500),
    SYSTEMS_ARCHITECT("Systems Architect", 1500),
    POLYMATH("Polymath", 3500),
    SAGE("Sage", 8000);

    companion object {
        fun forXP(xp: Int): ThinkerLevel = entries.lastOrNull { it.xpRequired <= xp } ?: CURIOUS_MIND
        fun nextLevel(xp: Int): ThinkerLevel? {
            val current = forXP(xp)
            val idx = entries.indexOf(current)
            return entries.getOrNull(idx + 1)
        }
    }
}
