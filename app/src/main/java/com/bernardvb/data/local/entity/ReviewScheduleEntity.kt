package com.bernardvb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review_schedule")
data class ReviewScheduleEntity(
    @PrimaryKey val id: String,
    val modelId: String,
    val userId: String,
    val firstViewedAt: Long,
    val nextReviewAt: Long,
    val reviewInterval: Int,     // Days: 1, 3, 7, 21, 60
    val reviewCount: Int = 0,
    val lastPracticeScore: Int? = null
)
