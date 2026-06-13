package com.bernardvb.data.local.dao

import androidx.room.*
import com.bernardvb.data.local.entity.ReviewScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewScheduleDao {

    @Query("SELECT * FROM review_schedule WHERE userId = :userId AND nextReviewAt <= :now ORDER BY nextReviewAt ASC")
    fun getDueReviews(userId: String, now: Long): Flow<List<ReviewScheduleEntity>>

    @Query("SELECT * FROM review_schedule WHERE userId = :userId AND modelId = :modelId")
    suspend fun getScheduleForModel(userId: String, modelId: String): ReviewScheduleEntity?

    @Upsert
    suspend fun upsertSchedule(schedule: ReviewScheduleEntity)

    @Update
    suspend fun updateSchedule(schedule: ReviewScheduleEntity)

    @Query("SELECT COUNT(*) FROM review_schedule WHERE userId = :userId AND nextReviewAt <= :now")
    suspend fun getDueCount(userId: String, now: Long): Int
}
