package com.bernardvb.data.local.dao

import androidx.room.*
import com.bernardvb.data.local.entity.MentalModelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MentalModelDao {

    @Query("SELECT * FROM mental_models ORDER BY name ASC")
    fun getAllModels(): Flow<List<MentalModelEntity>>

    @Query("SELECT * FROM mental_models WHERE domain = :domain ORDER BY name ASC")
    fun getModelsByDomain(domain: String): Flow<List<MentalModelEntity>>

    @Query("SELECT * FROM mental_models WHERE isFree = 1 ORDER BY name ASC")
    fun getFreeModels(): Flow<List<MentalModelEntity>>

    @Query("SELECT * FROM mental_models WHERE id = :id")
    suspend fun getModelById(id: String): MentalModelEntity?

    @Query("""
        SELECT * FROM mental_models
        WHERE name LIKE '%' || :query || '%'
           OR shortDesc LIKE '%' || :query || '%'
           OR origin LIKE '%' || :query || '%'
           OR tags LIKE '%' || :query || '%'
        ORDER BY
            CASE WHEN name LIKE :query || '%' THEN 0 ELSE 1 END,
            name ASC
    """)
    fun searchModels(query: String): Flow<List<MentalModelEntity>>

    @Query("SELECT * FROM mental_models WHERE id IN (:ids)")
    suspend fun getModelsByIds(ids: List<String>): List<MentalModelEntity>

    @Query("SELECT COUNT(*) FROM mental_models")
    suspend fun getCount(): Int

    @Upsert
    suspend fun upsertModels(models: List<MentalModelEntity>)

    @Upsert
    suspend fun upsertModel(model: MentalModelEntity)

    @Query("DELETE FROM mental_models")
    suspend fun deleteAll()

    @Query("SELECT * FROM mental_models WHERE lastUpdated > :since")
    suspend fun getModelsUpdatedSince(since: Long): List<MentalModelEntity>
}
