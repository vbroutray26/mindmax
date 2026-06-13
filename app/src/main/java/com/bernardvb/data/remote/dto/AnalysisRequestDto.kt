package com.bernardvb.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnalysisRequestDto(
    @Json(name = "situationText") val situationText: String,
    @Json(name = "moodContext") val moodContext: String?,
    @Json(name = "urgencyContext") val urgencyContext: String?,
    @Json(name = "deepAnalysis") val deepAnalysis: Boolean = false,
    @Json(name = "previousModelIds") val previousModelIds: List<String> = emptyList()
)
