package com.bernardvb.data.remote

import com.bernardvb.data.remote.dto.*
import retrofit2.http.*

interface BernardVBApiService {

    @POST("analyse")
    suspend fun analysesSituation(@Body request: AnalysisRequestDto): AnalysisResponseDto

    @GET("models")
    suspend fun syncModels(
        @Query("since") since: Long?,
        @Query("tier") tier: String
    ): ModelSyncResponseDto

    @POST("challenge/{challengeId}/respond")
    suspend fun submitChallengeResponse(
        @Path("challengeId") challengeId: String,
        @Body body: Map<String, String>
    ): ChallengeResponseResultDto

    @POST("journal/{entryId}/checkin")
    suspend fun submitCheckIn(
        @Path("entryId") entryId: String,
        @Body body: CheckInRequestDto
    ): CheckInResultDto

    @POST("progress/sync")
    suspend fun syncProgress(@Body progress: Map<String, Any>): SyncResultDto
}

data class ChallengeResponseResultDto(
    val feedback: ChallengeFeedbackDto,
    val xpEarned: Int,
    val score: Int
)

data class ChallengeFeedbackDto(
    val whatYouGotRight: String,
    val whatYouMissed: String,
    val masterPractitionerAddition: String
)

data class CheckInRequestDto(val rating: Int, val reflection: String)
data class CheckInResultDto(val decisionAccuracyDelta: Float, val newAccuracyScore: Float)
data class SyncResultDto(val synced: Boolean, val serverTimestamp: Long)
