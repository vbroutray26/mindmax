package com.bernardvb.data.local.dao

import androidx.room.*
import com.bernardvb.data.local.entity.AnalysisEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalysisDao {

    @Query("SELECT * FROM analyses WHERE userId = :userId ORDER BY createdAt DESC")
    fun getAnalysesForUser(userId: String): Flow<List<AnalysisEntity>>

    @Query("SELECT * FROM analyses WHERE userId = :userId ORDER BY createdAt DESC LIMIT :limit")
    fun getRecentAnalyses(userId: String, limit: Int = 10): Flow<List<AnalysisEntity>>

    @Query("SELECT * FROM analyses WHERE id = :id")
    suspend fun getAnalysisById(id: String): AnalysisEntity?

    @Query("SELECT COUNT(*) FROM analyses WHERE userId = :userId")
    suspend fun getAnalysisCount(userId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysis(analysis: AnalysisEntity)

    @Delete
    suspend fun deleteAnalysis(analysis: AnalysisEntity)

    @Query("DELETE FROM analyses WHERE userId = :userId AND createdAt < :before")
    suspend fun deleteOldAnalyses(userId: String, before: Long)
}
