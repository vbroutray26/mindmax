package com.bernardvb.di

import android.content.Context
import androidx.room.Room
import com.bernardvb.data.local.BernardVBDatabase
import com.bernardvb.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BernardVBDatabase =
        Room.databaseBuilder(context, BernardVBDatabase::class.java, BernardVBDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideMentalModelDao(db: BernardVBDatabase): MentalModelDao = db.mentalModelDao()
    @Provides fun provideAnalysisDao(db: BernardVBDatabase): AnalysisDao = db.analysisDao()
    @Provides fun provideJournalDao(db: BernardVBDatabase): JournalDao = db.journalDao()
    @Provides fun provideUserProgressDao(db: BernardVBDatabase): UserProgressDao = db.userProgressDao()
    @Provides fun provideReviewScheduleDao(db: BernardVBDatabase): ReviewScheduleDao = db.reviewScheduleDao()
}
