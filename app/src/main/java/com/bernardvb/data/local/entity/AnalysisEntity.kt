package com.bernardvb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "analyses")
data class AnalysisEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val situationText: String,
    val moodContext: String?,
    val urgencyContext: String?,
    val primaryModelId: String,
    val primaryContextApplication: String,
    val secondaryModelIds: String,   // JSON array
    val secondaryReasons: String,    // JSON array
    val blindSpotModelId: String?,
    val blindSpotText: String?,
    val conservativePath: String,    // JSON object
    val balancedPath: String,        // JSON object
    val boldPath: String,            // JSON object
    val isDeepAnalysis: Boolean,
    val deepSynthesis: String?,      // JSON object
    val xpEarned: Int,
    val createdAt: Long = System.currentTimeMillis()
)
