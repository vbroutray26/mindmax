package com.bernardvb.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bernardvb.domain.model.enums.Domain
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThinkingDNAScreen(
    onBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val progress by viewModel.progress.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("THINKING DNA", style = BernardType.LabelSmall) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (progress == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = BernardColors.AccentBlue)
            }
            return@Scaffold
        }

        val p = progress!!

        LazyColumn(
            contentPadding = PaddingValues(
                start = 20.dp, end = 20.dp,
                top = padding.calculateTopPadding() + 8.dp,
                bottom = 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("YOUR", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("THINKING\nDNA", style = BernardType.DisplayLarge, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Based on ${p.totalAnalyses} analyses across ${p.domainBreakdown.size} domains",
                        style = BernardType.BodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (p.topModels.isNotEmpty()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("MOST APPLIED MODELS", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        p.topModels.take(5).forEachIndexed { i, modelId ->
                            TopModelRow(rank = i + 1, modelId = modelId)
                        }
                    }
                }
            }

            if (p.domainBreakdown.isNotEmpty()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("DOMAIN DISTRIBUTION", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        val total = p.domainBreakdown.values.sum().toFloat().coerceAtLeast(1f)
                        val sorted = p.domainBreakdown.entries.sortedByDescending { it.value }
                        sorted.forEach { (domainId, count) ->
                            DomainBar(
                                domainId = domainId,
                                count = count,
                                fraction = count / total
                            )
                        }
                    }
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("COGNITIVE PROFILE", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    val profile = deriveCognitiveProfile(p.domainBreakdown)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            profile.forEach { (label, description) ->
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(label, style = BernardType.LabelSmall, color = BernardColors.AccentBlue)
                                    Text(description, style = BernardType.BodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("GAPS TO EXPLORE", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    val gaps = deriveGaps(p.domainBreakdown)
                    gaps.forEach { gap ->
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.surface
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("○", style = BernardType.BodyMedium, color = MaterialTheme.colorScheme.outlineVariant)
                                Text(gap, style = BernardType.BodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopModelRow(rank: Int, modelId: String) {
    val displayName = modelId
        .replace("-", " ")
        .split(" ")
        .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "#$rank",
            style = BernardType.LabelSmall,
            color = if (rank == 1) BernardColors.AccentBlue else MaterialTheme.colorScheme.outlineVariant,
            modifier = Modifier.width(32.dp)
        )
        Text(displayName, style = BernardType.BodyMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun DomainBar(domainId: String, count: Int, fraction: Float) {
    val domain = Domain.entries.find { it.name.lowercase().replace("_", "-") == domainId }
    val color = domain?.color ?: BernardColors.AccentBlue
    val label = domain?.displayName ?: domainId.replace("-", " ").split(" ")
        .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = BernardType.BodySmall, color = MaterialTheme.colorScheme.onBackground)
            Text("$count", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                Modifier
                    .fillMaxWidth(fraction)
                    .height(6.dp)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(color)
            )
        }
    }
}

private fun deriveCognitiveProfile(domains: Map<String, Int>): List<Pair<String, String>> {
    if (domains.isEmpty()) return listOf(
        "ANALYTICAL" to "You favour structured reasoning and evidence-based approaches.",
        "GROWTH-ORIENTED" to "You regularly reflect on decisions and seek to improve your thinking."
    )
    val top = domains.entries.maxByOrNull { it.value }?.key ?: ""
    val profile = mutableListOf<Pair<String, String>>()
    when {
        top.contains("decision") -> profile.add("DECISIVE" to "You lean toward structured decision frameworks and clear trade-off analysis.")
        top.contains("system") -> profile.add("SYSTEMIC" to "You naturally see connections and second-order effects others miss.")
        top.contains("philosophy") -> profile.add("PRINCIPLED" to "You ground decisions in core values and ethical frameworks.")
        top.contains("strategy") -> profile.add("STRATEGIC" to "You think in competitive landscapes and long-term positioning.")
        top.contains("science") -> profile.add("EMPIRICAL" to "You trust data and evidence over intuition alone.")
        else -> profile.add("BALANCED" to "You draw from multiple frameworks rather than defaulting to one domain.")
    }
    profile.add("REFLECTIVE" to "Your journal habit shows you close the loop — not just deciding, but learning from outcomes.")
    return profile
}

private fun deriveGaps(domains: Map<String, Int>): List<String> {
    val allDomains = listOf(
        "decision-making", "systems-thinking", "cognitive-biases",
        "philosophy-ethics", "strategy-business", "scientific-thinking",
        "psychology-behaviour", "economics-finance", "creativity-innovation"
    )
    return allDomains
        .filter { d -> (domains[d] ?: 0) == 0 }
        .take(4)
        .map { d -> d.replace("-", " ").split(" ").joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } } }
}
