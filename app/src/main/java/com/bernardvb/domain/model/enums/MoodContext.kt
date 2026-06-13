package com.bernardvb.domain.model.enums

enum class MoodContext(val displayName: String, val emoji: String) {
    CALM("Calm", "😌"),
    STRESSED("Stressed", "😰"),
    EXCITED("Excited", "⚡"),
    ANXIOUS("Anxious", "😟"),
    UNCERTAIN("Uncertain", "🤔"),
    OVERWHELMED("Overwhelmed", "😵")
}

enum class UrgencyContext(val displayName: String) {
    NOT_URGENT("Not Urgent"),
    MODERATELY_URGENT("Moderately Urgent"),
    VERY_URGENT("Very Urgent")
}
