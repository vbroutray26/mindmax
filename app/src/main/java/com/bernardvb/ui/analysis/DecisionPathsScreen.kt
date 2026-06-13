package com.bernardvb.ui.analysis

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bernardvb.domain.model.DecisionPath
import com.bernardvb.domain.model.enums.PathType
import com.bernardvb.ui.components.DecisionPathCard
import com.bernardvb.ui.theme.BernardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecisionPathsScreen(
    analysisId: String,
    onBack: () -> Unit,
    onSaveToJournal: () -> Unit,
    onDrillDown: () -> Unit,
    viewModel: AnalysisViewModel = hiltViewModel()
) {
    val analysis by viewModel.currentAnalysis.collectAsState()

    LaunchedEffect(analysisId) {
        if (analysis == null) viewModel.loadAnalysis(analysisId)
    }

    val a = analysis ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("3 PATHS", style = BernardType.LabelLarge)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val paths = listOf(
                a.decisionPaths.conservative,
                a.decisionPaths.balanced,
                a.decisionPaths.bold
            )

            items(paths) { path ->
                DecisionPathCard(path = path)
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onSaveToJournal,
                        modifier = Modifier.weight(1f).height(52.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Save to Journal", style = BernardType.BodyMedium)
                    }
                    Button(
                        onClick = onDrillDown,
                        modifier = Modifier.weight(1f).height(52.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Drill Down →", style = BernardType.BodyMedium)
                    }
                }
            }
        }
    }
}
