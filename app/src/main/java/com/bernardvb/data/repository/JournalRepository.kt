package com.bernardvb.data.repository

import com.bernardvb.domain.model.JournalEntry
import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    fun getAllEntries(): Flow<List<JournalEntry>>
    fun getRecentEntries(limit: Int): Flow<List<JournalEntry>>
    fun getDueCheckIns(): Flow<List<JournalEntry>>
    suspend fun getEntryById(id: String): JournalEntry?
    suspend fun saveEntry(entry: JournalEntry)
    suspend fun updateEntry(entry: JournalEntry)
    suspend fun getCount(): Int
}
