package com.bernardvb.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bernardvb.domain.model.enums.MoodContext
import com.bernardvb.ui.theme.BernardType

@Composable
fun MoodCheckRow(
    selectedMood: MoodContext?,
    onMoodSelected: (MoodContext?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            "How are you feeling?",
            style = BernardType.LabelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(MoodContext.entries) { mood ->
                FilterChip(
                    selected = selectedMood == mood,
                    onClick = { onMoodSelected(if (selectedMood == mood) null else mood) },
                    label = {
                        Text(
                            "${mood.emoji} ${mood.displayName}",
                            style = BernardType.LabelSmall
                        )
                    }
                )
            }
        }
    }
}
