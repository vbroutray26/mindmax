package com.bernardvb.ui.analysis

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bernardvb.ui.components.ModelCard
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrillDownScreen(
    analysisId: String,
    onBack: () -> Unit,
    onModelClick: (String) -> Unit,
    viewModel: AnalysisViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(analysisId) {
        viewModel.loadAnalysis(analysisId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DEEP SYNTHESIS", style = BernardType.LabelSmall) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when (val s = state) {
            is AnalysisState.Success -> {
                val analysis = s.analysis
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 20.dp, end = 20.dp,
                        top = padding.calculateTopPadding() + 8.dp,
                        bottom = 32.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("PRIMARY MODEL", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(analysis.primaryModel.name, style = BernardType.DisplayMedium)
                        }
                    }

                    analysis.deepSynthesis?.let { synthesis ->
                        item {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text("HOW IT APPLIES", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(synthesis.synthesis, style = BernardType.BodyLarge, color = MaterialTheme.colorScheme.onBackground)
                            }
                        }

                        item {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text("KEY INSIGHT", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = BernardColors.AccentBlue.copy(alpha = 0.1f)),
                                    shape = MaterialTheme.shapes.medium
                                ) {
                                    Text(
                                        synthesis.keyInsight,
                                        style = BernardType.BodyLarge,
                                        color = BernardColors.AccentBlue,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }
                        }

                        item {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text("NEXT ACTION", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Surface(
                                    shape = MaterialTheme.shapes.medium,
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text("→", style = BernardType.DisplaySmall, color = BernardColors.AccentBlue)
                                        Text(synthesis.actionableStep, style = BernardType.BodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        }
                    }

                    if (analysis.secondaryModels.isNotEmpty()) {
                        item {
                            Text("SUPPORTING MODELS", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        items(analysis.secondaryModels) { secondary ->
                            ModelCard(
                                id = secondary.modelId,
                                name = secondary.modelName,
                                domain = analysis.primaryModel.domain,
                                difficulty = com.bernardvb.domain.model.MentalModel.Difficulty.FOUNDATIONAL,
                                isLocked = false,
                                onClick = onModelClick
                            )
                        }
                    }

                    analysis.blindSpot?.let { blind ->
                        item {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("WATCH OUT FOR", style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                com.bernardvb.ui.components.BlindSpotAlert(warning = blind.warning)
                            }
                        }
                    }
                }
            }

            is AnalysisState.Loading -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = BernardColors.AccentBlue)
                }
            }

            else -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text("Analysis not found.", style = BernardType.BodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
