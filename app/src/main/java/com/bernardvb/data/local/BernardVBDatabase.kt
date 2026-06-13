package com.bernardvb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bernardvb.data.local.dao.*
import com.bernardvb.data.local.entity.*

@Database(
    entities = [
        MentalModelEntity::class,
        AnalysisEntity::class,
        JournalEntryEntity::class,
        UserProgressEntity::class,
        ReviewScheduleEntity::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class BernardVBDatabase : RoomDatabase() {
    abstract fun mentalModelDao(): MentalModelDao
    abstract fun analysisDao(): AnalysisDao
    abstract fun journalDao(): JournalDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun reviewScheduleDao(): ReviewScheduleDao

    companion object {
        const val DATABASE_NAME = "bernardvb.db"
    }
}
