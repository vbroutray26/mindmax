package com.bernardvb.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bernardvb.domain.model.enums.Domain
import com.bernardvb.ui.theme.BernardType

@Composable
fun DomainChip(
    label: String,
    domain: Domain,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) domain.color.copy(alpha = 0.15f)
    else MaterialTheme.colorScheme.surfaceVariant

    Row(
        modifier = modifier
            .background(backgroundColor, MaterialTheme.shapes.extraSmall)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraSmall,
            color = domain.color,
            modifier = Modifier.size(6.dp)
        ) {}
        Text(
            label,
            style = BernardType.LabelSmall,
            color = if (isSelected) domain.color else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
