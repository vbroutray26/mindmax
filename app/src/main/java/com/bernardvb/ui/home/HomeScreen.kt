package com.bernardvb.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bernardvb.domain.model.Analysis
import com.bernardvb.ui.components.StreakCounter
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    onAnalyseClick: () -> Unit,
    onAnalysisClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = greetingFor(uiState.userName),
                        style = BernardType.DisplaySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                StreakCounter(streak = uiState.currentStreak)
            }
        }

        item {
            SituationInputCard(onClick = onAnalyseClick)
        }

        if (uiState.recentAnalyses.isNotEmpty()) {
            item {
                Text(
                    text = "RECENT ANALYSES",
                    style = BernardType.LabelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            items(uiState.recentAnalyses) { analysis ->
                RecentAnalysisRow(
                    analysis = analysis,
                    onClick = { onAnalysisClick(analysis.id) }
                )
            }
        }
    }
}

@Composable
private fun SituationInputCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "🎙", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "Describe your situation…",
                style = BernardType.BodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun RecentAnalysisRow(analysis: Analysis, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = analysis.situationText.take(80).let { if (analysis.situationText.length > 80) "$it…" else it },
                style = BernardType.BodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = analysis.primaryModel.model.name,
                    style = BernardType.LabelSmall,
                    color = BernardColors.AccentBlue
                )
                analysis.secondaryModels.firstOrNull()?.let {
                    Text("·", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(
                        text = it.model.name,
                        style = BernardType.LabelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Text(
                text = formatRelativeDate(analysis.createdAt),
                style = BernardType.LabelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun greetingFor(name: String?): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val timeGreeting = when {
        hour < 12 -> "Good morning"
        hour < 17 -> "Good afternoon"
        else -> "Good evening"
    }
    return if (name.isNullOrBlank()) timeGreeting else "$timeGreeting, $name"
}

private fun formatRelativeDate(timestamp: Long): String {
    val diff = System.currentTimeMillis() - timestamp
    val days = diff / (1000 * 60 * 60 * 24)
    return when {
        days == 0L -> "Today"
        days == 1L -> "Yesterday"
        days < 7 -> "$days days ago"
        else -> SimpleDateFormat("MMM d", Locale.getDefault()).format(Date(timestamp))
    }
}
