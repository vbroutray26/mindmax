package com.bernardvb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntryEntity(
    @PrimaryKey val id: String,
    val analysisId: String,
    val situationTitle: String,
    val decisionChosen: String?,
    val checkIn7Date: Long?,
    val checkIn30Date: Long?,
    val checkIn90Date: Long?,
    val checkIn7Response: String?,   // JSON: {rating, reflection, respondedAt}
    val checkIn30Response: String?,
    val checkIn90Response: String?,
    val userTags: String,            // JSON array
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
