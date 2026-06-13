package com.bernardvb.ui.learn

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

@Composable
fun LearnScreen(
    onModelClick: (String) -> Unit,
    onPathClick: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text("LEARN", style = BernardType.DisplaySmall, color = MaterialTheme.colorScheme.onBackground)
        }

        // Today's Moment
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("TODAY'S MOMENT", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("🌅  Memento Mori", style = BernardType.DisplaySmall, color = MaterialTheme.colorScheme.onBackground)
                        Text(
                            "\"If today were your last Tuesday, which of this week's worries would still matter?\"",
                            style = BernardType.BodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Button(
                            onClick = { /* open moment */ },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Reflect →", style = BernardType.BodyMedium)
                        }
                    }
                }
            }
        }

        // Learning path progress
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("YOUR LEARNING PATH", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Decision Clarity", style = BernardType.BodyMedium, color = MaterialTheme.colorScheme.onSurface)
                            Text("7 / 20", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        LinearProgressIndicator(
                            progress = { 7f / 20f },
                            modifier = Modifier.fillMaxWidth(),
                            color = BernardColors.AccentBlue,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Next: The OODA Loop", style = BernardType.BodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            TextButton(onClick = { onModelClick("ooda-loop") }) {
                                Text("Start →", style = BernardType.LabelSmall, color = BernardColors.AccentBlue)
                            }
                        }
                    }
                }
            }
        }

        // Weekly challenge
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("THIS WEEK'S CHALLENGE", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)) {
                            Text("📋 Apply Inversion", style = BernardType.BodyMedium, color = MaterialTheme.colorScheme.onSurface)
                            Text("You have until Sunday · +50 XP", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Button(onClick = {}, shape = MaterialTheme.shapes.small) {
                            Text("Start", style = BernardType.LabelSmall)
                        }
                    }
                }
            }
        }
    }
}
