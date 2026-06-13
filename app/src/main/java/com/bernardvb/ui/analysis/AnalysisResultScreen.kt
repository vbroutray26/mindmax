package com.bernardvb.ui.analysis

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bernardvb.ui.components.BlindSpotAlert
import com.bernardvb.ui.components.DomainChip
import com.bernardvb.ui.components.ThinkingPulse
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisResultScreen(
    analysisId: String,
    onBack: () -> Unit,
    onDecisionPaths: () -> Unit,
    onModelClick: (String) -> Unit,
    viewModel: AnalysisViewModel = hiltViewModel()
) {
    val state by viewModel.analysisState.collectAsState()
    val analysis by viewModel.currentAnalysis.collectAsState()

    LaunchedEffect(analysisId) {
        if (analysis == null) viewModel.loadAnalysis(analysisId)
    }

    when (val s = state) {
        is AnalysisViewModel.AnalysisState.Loading -> ThinkingPulse()
        is AnalysisViewModel.AnalysisState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(s.message, style = BernardType.BodyMedium, color = BernardColors.BlindSpot)
            }
        }
        else -> {
            val a = analysis ?: return
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                            }
                        },
                        actions = {
                            IconButton(onClick = { /* Share */ }) {
                                Icon(Icons.Default.Share, "Share")
                            }
                        }
                    )
                }
            ) { padding ->
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 20.dp, end = 20.dp,
                        top = padding.calculateTopPadding() + 8.dp,
                        bottom = padding.calculateBottomPadding() + 20.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Primary model
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "⚡ PRIMARY MODEL",
                                style = BernardType.LabelSmall,
                                color = BernardColors.AccentBlue
                            )
                            Text(
                                text = a.primaryModel.model.name,
                                style = BernardType.DisplayMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = a.primaryModel.model.origin,
                                style = BernardType.LabelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            Text(
                                text = a.primaryModel.model.shortDesc,
                                style = BernardType.BodyLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    // Applied to situation
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "APPLIED TO YOUR SITUATION",
                                style = BernardType.LabelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = a.primaryModel.contextApplication,
                                style = BernardType.BodyLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    // Secondary models
                    if (a.secondaryModels.isNotEmpty()) {
                        item {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = "ALSO CONSIDER",
                                    style = BernardType.LabelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    items(a.secondaryModels) { sec ->
                                        DomainChip(
                                            label = sec.model.name,
                                            domain = sec.model.domain,
                                            onClick = { onModelClick(sec.model.id) }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Blind spot
                    a.blindSpot?.let { bs ->
                        item { BlindSpotAlert(alertText = bs.alertText) }
                    }

                    // CTA
                    item {
                        Button(
                            onClick = onDecisionPaths,
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text("See 3 Decision Paths →", style = BernardType.BodyMedium)
                        }
                    }
                }
            }
        }
    }
}
