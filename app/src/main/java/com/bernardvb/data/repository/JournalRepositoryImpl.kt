package com.bernardvb.data.repository

import com.bernardvb.data.local.dao.JournalDao
import com.bernardvb.data.local.entity.JournalEntryEntity
import com.bernardvb.domain.model.JournalEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JournalRepositoryImpl @Inject constructor(
    private val dao: JournalDao
) : JournalRepository {

    override fun getAllEntries(): Flow<List<JournalEntry>> =
        dao.getAllEntries().map { it.map { e -> e.toDomain() } }

    override fun getRecentEntries(limit: Int): Flow<List<JournalEntry>> =
        dao.getRecentEntries(limit).map { it.map { e -> e.toDomain() } }

    override fun getDueCheckIns(): Flow<List<JournalEntry>> =
        dao.getDueCheckIns(System.currentTimeMillis()).map { it.map { e -> e.toDomain() } }

    override suspend fun getEntryById(id: String): JournalEntry? =
        dao.getEntryById(id)?.toDomain()

    override suspend fun saveEntry(entry: JournalEntry) {
        dao.insertEntry(entry.toEntity())
    }

    override suspend fun updateEntry(entry: JournalEntry) {
        dao.updateEntry(entry.toEntity())
    }

    override suspend fun getCount(): Int = dao.getCount()

    private fun JournalEntryEntity.toDomain() = JournalEntry(
        id = id,
        analysisId = analysisId,
        situationTitle = situationTitle,
        modelsApplied = emptyList(),
        decisionChosen = decisionChosen,
        checkIn7Date = checkIn7Date,
        checkIn30Date = checkIn30Date,
        checkIn90Date = checkIn90Date,
        checkIn7Response = null,
        checkIn30Response = null,
        checkIn90Response = null,
        userTags = emptyList(),
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    private fun JournalEntry.toEntity() = JournalEntryEntity(
        id = id.ifEmpty { UUID.randomUUID().toString() },
        analysisId = analysisId,
        situationTitle = situationTitle,
        decisionChosen = decisionChosen,
        checkIn7Date = checkIn7Date,
        checkIn30Date = checkIn30Date,
        checkIn90Date = checkIn90Date,
        checkIn7Response = null,
        checkIn30Response = null,
        checkIn90Response = null,
        userTags = "[]",
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
