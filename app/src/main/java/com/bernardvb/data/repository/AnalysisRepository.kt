package com.bernardvb.data.repository

import com.bernardvb.domain.model.Analysis
import com.bernardvb.domain.model.enums.MoodContext
import com.bernardvb.domain.model.enums.UrgencyContext
import kotlinx.coroutines.flow.Flow

interface AnalysisRepository {
    fun getRecentAnalyses(userId: String, limit: Int = 10): Flow<List<Analysis>>
    suspend fun analysesSituation(
        situationText: String,
        mood: MoodContext?,
        urgency: UrgencyContext?,
        isDeep: Boolean,
        previousModelIds: List<String>
    ): Result<Analysis>
    suspend fun getAnalysisById(id: String): Analysis?
    suspend fun saveAnalysis(analysis: Analysis, userId: String)
}
