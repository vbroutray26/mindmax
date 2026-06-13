package com.bernardvb.domain.model

import com.bernardvb.domain.model.enums.Domain

data class MentalModel(
    val id: String,
    val name: String,
    val domain: Domain,
    val subdomain: String,
    val origin: String,
    val shortDesc: String,
    val fullDesc: String,
    val howToApply: String,
    val whenToUse: List<String>,
    val commonMistakes: List<String>,
    val pairingLogic: List<String>,
    val tags: List<String>,
    val thinkerProfiles: List<String>,
    val difficulty: Difficulty,
    val isFree: Boolean,
    val audioUrl: String? = null
) {
    enum class Difficulty(val displayName: String) {
        FOUNDATIONAL("Foundational"),
        INTERMEDIATE("Intermediate"),
        ADVANCED("Advanced")
    }
}
