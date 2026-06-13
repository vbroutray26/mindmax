package com.bernardvb.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bernardvb.domain.model.ThinkerLevel
import com.bernardvb.ui.components.XPProgressBar
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

@Composable
fun ProfileScreen(
    onThinkingDNA: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text("PROFILE", style = BernardType.DisplaySmall, color = MaterialTheme.colorScheme.onBackground)
        }

        // Avatar + level
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.padding(12.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        uiState.displayName,
                        style = BernardType.DisplaySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "${uiState.totalXP} Thinking Points",
                        style = BernardType.LabelLarge,
                        color = BernardColors.Gold
                    )
                }
            }
        }

        // XP bar
        item {
            XPProgressBar(
                currentXP = uiState.totalXP,
                currentLevel = uiState.currentLevel
            )
        }

        // Thinking DNA card
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("THINKING DNA", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    onClick = onThinkingDNA
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("🧬 Unlock your Thinking DNA after 10 analyses", style = BernardType.BodyMedium)
                        Text("Run more analyses to discover your patterns.", style = BernardType.BodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        // Stats
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("STATS", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    StatItem("${uiState.totalAnalyses}", "analyses")
                    StatItem("${uiState.currentStreak}", "streak")
                    StatItem("${uiState.totalChallenges}", "challenges")
                }
            }
        }

        // Settings
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("SETTINGS", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                listOf("Notifications", "Subscription", "Privacy", "Offline Library", "Help").forEach { item ->
                    ListItem(
                        headlineContent = { Text(item, style = BernardType.BodyMedium) },
                        trailingContent = { Text("›", style = BernardType.BodyMedium) }
                    )
                }
            }
        }
    }
}

@Composable
private fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = BernardType.DisplaySmall, color = MaterialTheme.colorScheme.onBackground)
        Text(label, style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
