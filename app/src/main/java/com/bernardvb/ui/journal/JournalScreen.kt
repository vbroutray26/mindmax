package com.bernardvb.ui.journal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bernardvb.domain.model.JournalEntry
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun JournalScreen(
    onEntryClick: (String) -> Unit,
    viewModel: JournalViewModel = hiltViewModel()
) {
    val entries by viewModel.entries.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text("JOURNAL", style = BernardType.DisplaySmall, color = MaterialTheme.colorScheme.onBackground)
        }

        if (entries.isEmpty()) {
            item {
                Box(Modifier.fillMaxWidth().padding(vertical = 48.dp), contentAlignment = Alignment.Center) {
                    Text(
                        "Your decision journal is empty.\nRun an analysis and save it here.",
                        style = BernardType.BodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        items(entries, key = { it.id }) { entry ->
            JournalEntryRow(entry = entry, onClick = { onEntryClick(entry.id) })
        }
    }
}

@Composable
private fun JournalEntryRow(entry: JournalEntry, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    entry.situationTitle,
                    style = BernardType.BodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2
                )
                Text(
                    SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date(entry.createdAt)),
                    style = BernardType.LabelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            CheckInStatusBadge(entry = entry)
        }
    }
}

@Composable
private fun CheckInStatusBadge(entry: JournalEntry) {
    val now = System.currentTimeMillis()
    val isDue = (entry.checkIn7Date != null && entry.checkIn7Response == null && entry.checkIn7Date <= now) ||
            (entry.checkIn30Date != null && entry.checkIn30Response == null && entry.checkIn30Date <= now) ||
            (entry.checkIn90Date != null && entry.checkIn90Response == null && entry.checkIn90Date <= now)
    val hasScheduled = entry.checkIn7Date != null || entry.checkIn30Date != null || entry.checkIn90Date != null

    if (isDue) {
        Badge(containerColor = BernardColors.Gold) {
            Text("Due", style = BernardType.LabelSmall)
        }
    } else if (hasScheduled) {
        Badge(containerColor = BernardColors.StrategyGreen) {
            Text("✓", style = BernardType.LabelSmall)
        }
    }
}
