package com.bernardvb.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.bernardvb.domain.model.MentalModel
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

@Composable
fun ModelCard(
    model: MentalModel,
    isLocked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .alpha(if (isLocked) 0.6f else 1f),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Domain colour dot
            Surface(
                shape = MaterialTheme.shapes.extraSmall,
                color = model.domain.color,
                modifier = Modifier.size(8.dp)
            ) {}

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    model.name,
                    style = BernardType.BodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        model.domain.displayName,
                        style = BernardType.LabelSmall,
                        color = model.domain.color
                    )
                    Text("·", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(
                        model.difficulty.displayName,
                        style = BernardType.LabelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (isLocked) {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "Pro only",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
