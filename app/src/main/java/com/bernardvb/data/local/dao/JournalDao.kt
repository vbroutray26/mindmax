package com.bernardvb.data.local.dao

import androidx.room.*
import com.bernardvb.data.local.entity.JournalEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Query("SELECT * FROM journal_entries ORDER BY createdAt DESC")
    fun getAllEntries(): Flow<List<JournalEntryEntity>>

    @Query("SELECT * FROM journal_entries ORDER BY createdAt DESC LIMIT :limit")
    fun getRecentEntries(limit: Int): Flow<List<JournalEntryEntity>>

    @Query("SELECT * FROM journal_entries WHERE id = :id")
    suspend fun getEntryById(id: String): JournalEntryEntity?

    @Query("""
        SELECT * FROM journal_entries
        WHERE (checkIn7Date IS NOT NULL AND checkIn7Response IS NULL AND checkIn7Date <= :now)
           OR (checkIn30Date IS NOT NULL AND checkIn30Response IS NULL AND checkIn30Date <= :now)
           OR (checkIn90Date IS NOT NULL AND checkIn90Response IS NULL AND checkIn90Date <= :now)
        ORDER BY createdAt DESC
    """)
    fun getDueCheckIns(now: Long): Flow<List<JournalEntryEntity>>

    @Query("SELECT COUNT(*) FROM journal_entries")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: JournalEntryEntity)

    @Update
    suspend fun updateEntry(entry: JournalEntryEntity)

    @Delete
    suspend fun deleteEntry(entry: JournalEntryEntity)
}
