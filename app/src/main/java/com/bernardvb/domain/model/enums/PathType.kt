package com.bernardvb.domain.model.enums

import androidx.compose.ui.graphics.Color
import com.bernardvb.ui.theme.BernardColors

enum class PathType(val displayName: String, val color: Color, val emoji: String) {
    CONSERVATIVE("Conservative", BernardColors.ConservativePath, "🟢"),
    BALANCED("Balanced", BernardColors.BalancedPath, "🔵"),
    BOLD("Bold", BernardColors.BoldPath, "🟡")
}
