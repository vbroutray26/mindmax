package com.bernardvb.data.repository

import com.bernardvb.domain.model.MentalModel
import com.bernardvb.domain.model.enums.Domain
import kotlinx.coroutines.flow.Flow

interface ModelRepository {
    fun getAllModels(): Flow<List<MentalModel>>
    fun getModelsByDomain(domain: Domain): Flow<List<MentalModel>>
    fun getFreeModels(): Flow<List<MentalModel>>
    fun searchModels(query: String): Flow<List<MentalModel>>
    suspend fun getModelById(id: String): MentalModel?
    suspend fun getModelsByIds(ids: List<String>): List<MentalModel>
    suspend fun syncModels(isPro: Boolean): Result<Int>
    suspend fun seedInitialModels()
}
