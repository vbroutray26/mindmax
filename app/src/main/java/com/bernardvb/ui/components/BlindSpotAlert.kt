package com.bernardvb.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

@Composable
fun BlindSpotAlert(alertText: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BernardColors.BlindSpot.copy(alpha = 0.08f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            Icons.Default.Warning,
            contentDescription = "Blind spot",
            tint = BernardColors.BlindSpot,
            modifier = Modifier.size(20.dp).padding(top = 2.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "BLIND SPOT DETECTED",
                style = BernardType.LabelSmall,
                color = BernardColors.BlindSpot
            )
            Text(
                alertText,
                style = BernardType.BodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
