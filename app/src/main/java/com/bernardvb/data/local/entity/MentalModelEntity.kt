package com.bernardvb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bernardvb.domain.model.MentalModel
import com.bernardvb.domain.model.enums.Domain

@Entity(tableName = "mental_models")
data class MentalModelEntity(
    @PrimaryKey val id: String,
    val name: String,
    val domain: String,
    val subdomain: String,
    val origin: String,
    val shortDesc: String,
    val fullDesc: String,
    val howToApply: String,
    val whenToUse: String,       // JSON array
    val commonMistakes: String,  // JSON array
    val pairingLogic: String,    // JSON array of IDs
    val tags: String,            // JSON array
    val thinkerProfiles: String, // JSON array
    val difficulty: String,
    val isFree: Boolean,
    val audioUrl: String?,
    val lastUpdated: Long = System.currentTimeMillis()
) {
    fun toDomain(jsonParser: (String) -> List<String>): MentalModel = MentalModel(
        id = id,
        name = name,
        domain = Domain.fromId(domain),
        subdomain = subdomain,
        origin = origin,
        shortDesc = shortDesc,
        fullDesc = fullDesc,
        howToApply = howToApply,
        whenToUse = jsonParser(whenToUse),
        commonMistakes = jsonParser(commonMistakes),
        pairingLogic = jsonParser(pairingLogic),
        tags = jsonParser(tags),
        thinkerProfiles = jsonParser(thinkerProfiles),
        difficulty = MentalModel.Difficulty.entries.firstOrNull { it.name.lowercase() == difficulty }
            ?: MentalModel.Difficulty.FOUNDATIONAL,
        isFree = isFree,
        audioUrl = audioUrl
    )
}
