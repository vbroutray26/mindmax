package com.bernardvb.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnalysisResponseDto(
    @Json(name = "analysisId") val analysisId: String,
    @Json(name = "primaryModel") val primaryModel: ModelApplicationDto,
    @Json(name = "secondaryModels") val secondaryModels: List<SecondaryModelDto>,
    @Json(name = "blindSpot") val blindSpot: BlindSpotDto?,
    @Json(name = "decisionPaths") val decisionPaths: DecisionPathsDto,
    @Json(name = "deepSynthesis") val deepSynthesis: DeepSynthesisDto?,
    @Json(name = "xpEarned") val xpEarned: Int,
    @Json(name = "usageRemaining") val usageRemaining: Int?
)

@JsonClass(generateAdapter = true)
data class ModelApplicationDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "origin") val origin: String,
    @Json(name = "shortDesc") val shortDesc: String,
    @Json(name = "contextApplication") val contextApplication: String
)

@JsonClass(generateAdapter = true)
data class SecondaryModelDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "reason") val reason: String
)

@JsonClass(generateAdapter = true)
data class BlindSpotDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "alertText") val alertText: String
)

@JsonClass(generateAdapter = true)
data class DecisionPathsDto(
    @Json(name = "conservative") val conservative: DecisionPathDto,
    @Json(name = "balanced") val balanced: DecisionPathDto,
    @Json(name = "bold") val bold: DecisionPathDto
)

@JsonClass(generateAdapter = true)
data class DecisionPathDto(
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "modelJustification") val modelJustification: String,
    @Json(name = "riskLevel") val riskLevel: String,
    @Json(name = "rewardLevel") val rewardLevel: String
)

@JsonClass(generateAdapter = true)
data class DeepSynthesisDto(
    @Json(name = "convergence") val convergence: String,
    @Json(name = "tension") val tension: String,
    @Json(name = "recommendation") val recommendation: String,
    @Json(name = "overlookedModel") val overlookedModel: String
)

@JsonClass(generateAdapter = true)
data class ModelSyncResponseDto(
    @Json(name = "models") val models: List<ModelDto>,
    @Json(name = "totalCount") val totalCount: Int,
    @Json(name = "lastUpdated") val lastUpdated: Long
)

@JsonClass(generateAdapter = true)
data class ModelDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "domain") val domain: String,
    @Json(name = "subdomain") val subdomain: String,
    @Json(name = "origin") val origin: String,
    @Json(name = "shortDesc") val shortDesc: String,
    @Json(name = "fullDesc") val fullDesc: String,
    @Json(name = "howToApply") val howToApply: String,
    @Json(name = "whenToUse") val whenToUse: List<String>,
    @Json(name = "commonMistakes") val commonMistakes: List<String>,
    @Json(name = "pairingLogic") val pairingLogic: List<String>,
    @Json(name = "tags") val tags: List<String>,
    @Json(name = "thinkerProfiles") val thinkerProfiles: List<String>,
    @Json(name = "difficulty") val difficulty: String,
    @Json(name = "isFree") val isFree: Boolean,
    @Json(name = "audioUrl") val audioUrl: String?
)
