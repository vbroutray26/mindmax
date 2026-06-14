package com.bernardvb.ui.journal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bernardvb.domain.model.JournalEntry
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalDetailScreen(
    entryId: String,
    onBack: () -> Unit,
    viewModel: JournalViewModel = hiltViewModel()
) {
    val entries by viewModel.entries.collectAsState()
    val entry = entries.find { it.id == entryId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("JOURNAL", style = BernardType.LabelSmall) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (entry == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = BernardColors.AccentBlue)
            }
        } else {
            JournalDetailContent(entry = entry, padding = padding)
        }
    }
}

@Composable
private fun JournalDetailContent(entry: JournalEntry, padding: PaddingValues) {
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")

    LazyColumn(
        contentPadding = PaddingValues(
            start = 20.dp, end = 20.dp,
            top = padding.calculateTopPadding() + 8.dp,
            bottom = 32.dp
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    entry.createdAt.format(formatter).uppercase(),
                    style = BernardType.LabelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(entry.situationSummary, style = BernardType.DisplaySmall, color = MaterialTheme.colorScheme.onBackground)
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("CHOSEN FRAMEWORK", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Card(
                    colors = CardDefaults.cardColors(containerColor = BernardColors.AccentBlue.copy(alpha = 0.08f)),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(entry.chosenPath, style = BernardType.BodyLarge, color = BernardColors.AccentBlue)
                        Text("Applied via ${entry.primaryModelId}", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        if (entry.userNotes.isNotBlank()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("YOUR NOTES", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(entry.userNotes, style = BernardType.BodyLarge, color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }

        if (entry.checkIns.isNotEmpty()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("CHECK-INS", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    entry.checkIns.forEach { checkIn ->
                        CheckInCard(checkIn)
                    }
                }
            }
        }

        entry.nextCheckIn?.let { nextDate ->
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text("NEXT CHECK-IN", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(nextDate.format(formatter), style = BernardType.BodyMedium, color = MaterialTheme.colorScheme.onBackground)
                        }
                        Text("⏰", style = BernardType.DisplaySmall)
                    }
                }
            }
        }
    }
}

@Composable
private fun CheckInCard(checkIn: com.bernardvb.domain.model.CheckInResponse) {
    val formatter = DateTimeFormatter.ofPattern("d MMM")
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(checkIn.checkInDate.format(formatter).uppercase(), style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                AccuracyChip(accuracy = checkIn.modelAccuracy)
            }
            if (checkIn.reflection.isNotBlank()) {
                Text(checkIn.reflection, style = BernardType.BodyMedium, color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}

@Composable
private fun AccuracyChip(accuracy: Float) {
    val (label, color) = when {
        accuracy >= 0.8f -> "Very accurate" to BernardColors.AccentBlue
        accuracy >= 0.6f -> "Mostly accurate" to com.bernardvb.ui.theme.BernardColors.Gold
        accuracy >= 0.4f -> "Somewhat accurate" to MaterialTheme.colorScheme.onSurfaceVariant
        else -> "Missed the mark" to MaterialTheme.colorScheme.error
    }
    Surface(
        shape = MaterialTheme.shapes.small,
        color = color.copy(alpha = 0.12f)
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            style = BernardType.LabelSmall,
            color = color
        )
    }
}
