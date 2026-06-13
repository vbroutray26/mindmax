package com.bernardvb.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bernardvb.domain.model.ThinkerLevel
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

@Composable
fun XPProgressBar(
    currentXP: Int,
    currentLevel: ThinkerLevel,
    modifier: Modifier = Modifier
) {
    val nextLevel = ThinkerLevel.nextLevel(currentXP)
    val progress = if (nextLevel != null) {
        val range = nextLevel.xpRequired - currentLevel.xpRequired
        val earned = currentXP - currentLevel.xpRequired
        (earned.toFloat() / range).coerceIn(0f, 1f)
    } else 1f

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(currentLevel.displayName, style = BernardType.LabelLarge, color = MaterialTheme.colorScheme.onBackground)
            Text(
                if (nextLevel != null) "${currentXP} / ${nextLevel.xpRequired} XP" else "MAX",
                style = BernardType.LabelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(4.dp),
            color = BernardColors.AccentBlue,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
        if (nextLevel != null) {
            Text(
                "Next: ${nextLevel.displayName}",
                style = BernardType.LabelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
