package com.bernardvb.domain.model

import com.bernardvb.domain.model.enums.MoodContext
import com.bernardvb.domain.model.enums.PathType
import com.bernardvb.domain.model.enums.UrgencyContext

data class Analysis(
    val id: String,
    val situationText: String,
    val moodContext: MoodContext?,
    val urgencyContext: UrgencyContext?,
    val primaryModel: ModelApplication,
    val secondaryModels: List<SecondaryModel>,
    val blindSpot: BlindSpot?,
    val decisionPaths: DecisionPaths,
    val deepSynthesis: DeepSynthesis?,
    val isDeepAnalysis: Boolean,
    val xpEarned: Int,
    val usageRemaining: Int?,
    val createdAt: Long
)

data class ModelApplication(
    val model: MentalModel,
    val contextApplication: String
)

data class SecondaryModel(
    val model: MentalModel,
    val reason: String
)

data class BlindSpot(
    val model: MentalModel,
    val alertText: String
)

data class DecisionPaths(
    val conservative: DecisionPath,
    val balanced: DecisionPath,
    val bold: DecisionPath
)

data class DecisionPath(
    val type: PathType,
    val title: String,
    val description: String,
    val modelJustification: String,
    val riskLevel: RiskLevel,
    val rewardLevel: RewardLevel
) {
    enum class RiskLevel { LOW, MEDIUM, HIGH }
    enum class RewardLevel { LOW, MEDIUM, HIGH, VERY_HIGH }
}

data class DeepSynthesis(
    val convergence: String,
    val tension: String,
    val recommendation: String,
    val overlookedModel: String
)
