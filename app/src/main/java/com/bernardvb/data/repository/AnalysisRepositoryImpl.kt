package com.bernardvb.data.repository

import com.bernardvb.data.local.dao.AnalysisDao
import com.bernardvb.data.local.entity.AnalysisEntity
import com.bernardvb.data.remote.BernardVBApiService
import com.bernardvb.data.remote.dto.AnalysisRequestDto
import com.bernardvb.domain.model.*
import com.bernardvb.domain.model.enums.MoodContext
import com.bernardvb.domain.model.enums.PathType
import com.bernardvb.domain.model.enums.UrgencyContext
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalysisRepositoryImpl @Inject constructor(
    private val dao: AnalysisDao,
    private val api: BernardVBApiService,
    private val modelRepository: ModelRepository,
    private val moshi: Moshi
) : AnalysisRepository {

    override fun getRecentAnalyses(userId: String, limit: Int): Flow<List<Analysis>> =
        dao.getRecentAnalyses(userId, limit).map { entities ->
            entities.mapNotNull { it.toDomain() }
        }

    override suspend fun analysesSituation(
        situationText: String,
        mood: MoodContext?,
        urgency: UrgencyContext?,
        isDeep: Boolean,
        previousModelIds: List<String>
    ): Result<Analysis> = runCatching {
        val request = AnalysisRequestDto(
            situationText = situationText,
            moodContext = mood?.name?.lowercase(),
            urgencyContext = urgency?.name?.lowercase(),
            deepAnalysis = isDeep,
            previousModelIds = previousModelIds
        )
        val dto = api.analysesSituation(request)

        val primaryModel = modelRepository.getModelById(dto.primaryModel.id)
            ?: throw IllegalStateException("Primary model not found: ${dto.primaryModel.id}")

        val secondaryModels = dto.secondaryModels.mapNotNull { sec ->
            modelRepository.getModelById(sec.id)?.let { model ->
                SecondaryModel(model, sec.reason)
            }
        }

        val blindSpot = dto.blindSpot?.let { bs ->
            modelRepository.getModelById(bs.id)?.let { model ->
                BlindSpot(model, bs.alertText)
            }
        }

        val paths = dto.decisionPaths
        Analysis(
            id = dto.analysisId,
            situationText = situationText,
            moodContext = mood,
            urgencyContext = urgency,
            primaryModel = ModelApplication(primaryModel, dto.primaryModel.contextApplication),
            secondaryModels = secondaryModels,
            blindSpot = blindSpot,
            decisionPaths = DecisionPaths(
                conservative = paths.conservative.toDomain(PathType.CONSERVATIVE),
                balanced = paths.balanced.toDomain(PathType.BALANCED),
                bold = paths.bold.toDomain(PathType.BOLD)
            ),
            deepSynthesis = dto.deepSynthesis?.let { ds ->
                DeepSynthesis(ds.convergence, ds.tension, ds.recommendation, ds.overlookedModel)
            },
            isDeepAnalysis = isDeep,
            xpEarned = dto.xpEarned,
            usageRemaining = dto.usageRemaining,
            createdAt = System.currentTimeMillis()
        )
    }

    override suspend fun getAnalysisById(id: String): Analysis? =
        dao.getAnalysisById(id)?.toDomain()

    override suspend fun saveAnalysis(analysis: Analysis, userId: String) {
        dao.insertAnalysis(analysis.toEntity(userId))
    }

    private fun com.bernardvb.data.remote.dto.DecisionPathDto.toDomain(type: PathType) = DecisionPath(
        type = type,
        title = title,
        description = description,
        modelJustification = modelJustification,
        riskLevel = DecisionPath.RiskLevel.entries.firstOrNull {
            it.name.lowercase().replace("_", "-") == riskLevel
        } ?: DecisionPath.RiskLevel.MEDIUM,
        rewardLevel = DecisionPath.RewardLevel.entries.firstOrNull {
            it.name.lowercase().replace("_", "-") == rewardLevel
        } ?: DecisionPath.RewardLevel.MEDIUM
    )

    private fun Analysis.toEntity(userId: String) = AnalysisEntity(
        id = id,
        userId = userId,
        situationText = situationText,
        moodContext = moodContext?.name?.lowercase(),
        urgencyContext = urgencyContext?.name?.lowercase(),
        primaryModelId = primaryModel.model.id,
        primaryContextApplication = primaryModel.contextApplication,
        secondaryModelIds = "[${secondaryModels.joinToString(",") { "\"${it.model.id}\"" }}]",
        secondaryReasons = "[${secondaryModels.joinToString(",") { "\"${it.reason.replace("\"", "\\\"")}\"" }}]",
        blindSpotModelId = blindSpot?.model?.id,
        blindSpotText = blindSpot?.alertText,
        conservativePath = decisionPaths.conservative.toJson(),
        balancedPath = decisionPaths.balanced.toJson(),
        boldPath = decisionPaths.bold.toJson(),
        isDeepAnalysis = isDeepAnalysis,
        deepSynthesis = null,
        xpEarned = xpEarned,
        createdAt = createdAt
    )

    private fun DecisionPath.toJson(): String =
        """{"title":"$title","description":"${description.replace("\"","\\\"")}","modelJustification":"$modelJustification","riskLevel":"${riskLevel.name.lowercase()}","rewardLevel":"${rewardLevel.name.lowercase()}"}"""

    private fun AnalysisEntity.toDomain(): Analysis? = null // Full mapping requires model lookup — done via ViewModel
}
