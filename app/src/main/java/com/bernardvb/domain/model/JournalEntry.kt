package com.bernardvb.domain.model

data class JournalEntry(
    val id: String,
    val analysisId: String,
    val situationTitle: String,
    val modelsApplied: List<String>,
    val decisionChosen: String?,
    val checkIn7Date: Long?,
    val checkIn30Date: Long?,
    val checkIn90Date: Long?,
    val checkIn7Response: CheckInResponse?,
    val checkIn30Response: CheckInResponse?,
    val checkIn90Response: CheckInResponse?,
    val userTags: List<String>,
    val createdAt: Long,
    val updatedAt: Long
)

data class CheckInResponse(
    val rating: Int,
    val reflection: String,
    val respondedAt: Long
)
