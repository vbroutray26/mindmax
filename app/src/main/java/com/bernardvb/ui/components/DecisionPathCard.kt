package com.bernardvb.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bernardvb.domain.model.DecisionPath
import com.bernardvb.ui.theme.BernardType

@Composable
fun DecisionPathCard(path: DecisionPath, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = path.type.color,
                shape = MaterialTheme.shapes.large
            ),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(path.type.emoji, style = MaterialTheme.typography.titleMedium)
                Text(
                    path.type.displayName.uppercase(),
                    style = BernardType.LabelLarge,
                    color = path.type.color
                )
            }
            Text(
                path.title,
                style = BernardType.DisplaySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                path.description,
                style = BernardType.BodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                "Framework: ${path.modelJustification}",
                style = BernardType.LabelLarge,
                color = path.type.color
            )
        }
    }
}
