package com.bernardvb.ui.navigation

sealed class Screen(val route: String) {
    // Bottom nav tabs
    data object Home : Screen("home")
    data object Library : Screen("library")
    data object Learn : Screen("learn")
    data object Journal : Screen("journal")
    data object Profile : Screen("profile")

    // Sub-screens
    data object SituationInput : Screen("situation_input")
    data object ThinkingPulse : Screen("thinking_pulse")
    data object AnalysisResult : Screen("analysis_result/{analysisId}") {
        fun route(id: String) = "analysis_result/$id"
    }
    data object DecisionPaths : Screen("decision_paths/{analysisId}") {
        fun route(id: String) = "decision_paths/$id"
    }
    data object DrillDown : Screen("drill_down/{analysisId}") {
        fun route(id: String) = "drill_down/$id"
    }
    data object ModelDetail : Screen("model_detail/{modelId}") {
        fun route(id: String) = "model_detail/$id"
    }
    data object LearningPath : Screen("learning_path/{pathId}") {
        fun route(id: String) = "learning_path/$id"
    }
    data object JournalDetail : Screen("journal_detail/{entryId}") {
        fun route(id: String) = "journal_detail/$id"
    }
    data object ThinkingDNA : Screen("thinking_dna")
    data object Paywall : Screen("paywall/{trigger}") {
        fun route(trigger: String) = "paywall/$trigger"
    }
}
