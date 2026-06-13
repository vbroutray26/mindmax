package com.bernardvb.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

@Composable
fun StreakCounter(streak: Int, modifier: Modifier = Modifier) {
    val color = when {
        streak >= 30 -> BernardColors.Gold
        streak >= 7 -> BernardColors.AccentBlue
        else -> BernardColors.StreakAmber
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text("🔥", style = MaterialTheme.typography.titleMedium)
        Text(
            text = streak.toString(),
            style = BernardType.LabelLarge,
            color = color
        )
    }
}
