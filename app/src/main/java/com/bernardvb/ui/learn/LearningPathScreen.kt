package com.bernardvb.ui.learn

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

private data class PathModel(
    val id: String,
    val name: String,
    val difficulty: String,
    val completed: Boolean,
    val locked: Boolean = false
)

private val learningPaths = mapOf(
    "decision-clarity" to Pair(
        "Decision Clarity",
        listOf(
            PathModel("first-principles-thinking", "First Principles Thinking", "Intermediate", completed = true),
            PathModel("inversion", "Inversion", "Foundational", completed = true),
            PathModel("second-order-thinking", "Second-Order Thinking", "Intermediate", completed = true),
            PathModel("premortem-analysis", "Premortem Analysis", "Intermediate", completed = true),
            PathModel("reversible-vs-irreversible-decisions", "Reversible vs Irreversible", "Foundational", completed = true),
            PathModel("the-eisenhower-matrix", "Eisenhower Matrix", "Foundational", completed = true),
            PathModel("the-80-20-rule", "80/20 Rule", "Foundational", completed = true),
            PathModel("the-ooda-loop", "OODA Loop", "Advanced", completed = false),
            PathModel("bayesian-reasoning", "Bayesian Reasoning", "Advanced", completed = false),
            PathModel("the-cynefin-framework", "Cynefin Framework", "Advanced", completed = false),
            PathModel("expected-value", "Expected Value", "Intermediate", completed = false, locked = true),
            PathModel("margin-of-safety", "Margin of Safety", "Intermediate", completed = false, locked = true),
            PathModel("the-10-10-10-rule", "10/10/10 Rule", "Foundational", completed = false, locked = true),
            PathModel("regret-minimisation-framework", "Regret Minimisation", "Foundational", completed = false, locked = true),
            PathModel("satisficing-vs-maximising", "Satisficing vs Maximising", "Foundational", completed = false, locked = true)
        )
    ),
    "cognitive-immunity" to Pair(
        "Cognitive Immunity",
        listOf(
            PathModel("confirmation-bias", "Confirmation Bias", "Foundational", completed = true),
            PathModel("availability-heuristic", "Availability Heuristic", "Foundational", completed = true),
            PathModel("survivorship-bias", "Survivorship Bias", "Foundational", completed = false),
            PathModel("dunning-kruger-effect", "Dunning-Kruger Effect", "Foundational", completed = false),
            PathModel("sunk-cost-fallacy", "Sunk Cost Fallacy", "Foundational", completed = false),
            PathModel("planning-fallacy", "Planning Fallacy", "Foundational", completed = false),
            PathModel("anchoring-bias", "Anchoring Bias", "Foundational", completed = false, locked = true),
            PathModel("framing-effect", "Framing Effect", "Intermediate", completed = false, locked = true),
            PathModel("blind-spot-bias", "Blind Spot Bias", "Intermediate", completed = false, locked = true)
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningPathScreen(
    pathId: String,
    onBack: () -> Unit,
    onModelClick: (String) -> Unit
) {
    val (pathName, models) = learningPaths[pathId] ?: learningPaths.values.first()
    val completedCount = models.count { it.completed }
    val totalCount = models.size
    val progress = completedCount.toFloat() / totalCount

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LEARNING PATH", style = BernardType.LabelSmall) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = 20.dp, end = 20.dp,
                top = padding.calculateTopPadding() + 8.dp,
                bottom = 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(pathName.uppercase(), style = BernardType.DisplayMedium, color = MaterialTheme.colorScheme.onBackground)
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("$completedCount of $totalCount models", style = BernardType.BodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("${(progress * 100).toInt()}%", style = BernardType.LabelSmall, color = BernardColors.AccentBlue)
                    }
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth(),
                        color = BernardColors.AccentBlue,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }

            item {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            }

            itemsIndexed(models) { index, model ->
                PathModelRow(
                    index = index + 1,
                    model = model,
                    onClick = { if (!model.locked) onModelClick(model.id) }
                )
            }
        }
    }
}

@Composable
private fun PathModelRow(
    index: Int,
    model: PathModel,
    onClick: () -> Unit
) {
    val iconTint = when {
        model.completed -> BernardColors.AccentBlue
        model.locked -> MaterialTheme.colorScheme.outlineVariant
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    val textColor = if (model.locked) MaterialTheme.colorScheme.outlineVariant else MaterialTheme.colorScheme.onBackground

    Surface(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        color = if (model.completed) BernardColors.AccentBlue.copy(alpha = 0.06f) else MaterialTheme.colorScheme.surface,
        enabled = !model.locked
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = index.toString().padStart(2, '0'),
                style = BernardType.LabelSmall,
                color = iconTint
            )
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(model.name, style = BernardType.BodyMedium, color = textColor)
                Text(model.difficulty, style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.outlineVariant)
            }
            when {
                model.completed -> Icon(Icons.Default.CheckCircle, contentDescription = "Completed", tint = BernardColors.AccentBlue, modifier = Modifier.size(20.dp))
                model.locked -> Icon(Icons.Default.Lock, contentDescription = "Locked", tint = MaterialTheme.colorScheme.outlineVariant, modifier = Modifier.size(18.dp))
                else -> Text("→", style = BernardType.BodyMedium, color = BernardColors.AccentBlue)
            }
        }
    }
}
