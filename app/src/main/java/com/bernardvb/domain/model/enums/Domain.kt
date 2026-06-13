package com.bernardvb.domain.model.enums

import androidx.compose.ui.graphics.Color
import com.bernardvb.ui.theme.BernardColors

enum class Domain(val displayName: String, val color: Color) {
    DECISION_MAKING("Decision Making", BernardColors.DecisionBlue),
    COGNITIVE_RAZORS("Cognitive Razors", BernardColors.RazorGold),
    SYSTEMS_THINKING("Systems Thinking", BernardColors.SystemsTeal),
    COGNITIVE_BIASES("Cognitive Biases", BernardColors.BiasRed),
    PHILOSOPHY_ETHICS("Philosophy & Ethics", BernardColors.PhilosophyPurple),
    STRATEGY_BUSINESS("Strategy & Business", BernardColors.StrategyGreen),
    SCIENTIFIC_THINKING("Scientific Thinking", BernardColors.ScienceCyan),
    PSYCHOLOGY_BEHAVIOUR("Psychology & Behaviour", BernardColors.PsychPink),
    ECONOMICS_FINANCE("Economics & Finance", BernardColors.EconomicsAmber),
    LEADERSHIP_MANAGEMENT("Leadership & Management", BernardColors.LeadershipViolet),
    CREATIVITY_INNOVATION("Creativity & Innovation", BernardColors.CreativityOrange),
    COMMUNICATION_RHETORIC("Communication & Rhetoric", BernardColors.CommIndigo),
    LEARNING_KNOWLEDGE("Learning & Knowledge", BernardColors.LearningLime),
    NATURE_EVOLUTION("Nature & Evolution", BernardColors.EvolutionEmerald),
    CHANGE_TRANSFORMATION("Change & Transformation", BernardColors.ChangeRose),
    GEOPOLITICS_SOCIOLOGY("Geopolitics & Sociology", BernardColors.GeopolBrown);

    companion object {
        fun fromId(id: String): Domain = entries.firstOrNull { it.name.lowercase().replace("_", "-") == id }
            ?: DECISION_MAKING
    }
}
