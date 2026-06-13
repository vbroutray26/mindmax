package com.bernardvb.ui.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bernardvb.ui.components.DomainChip
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelDetailScreen(
    modelId: String,
    onBack: () -> Unit,
    onRelatedModelClick: (String) -> Unit,
    viewModel: ModelDetailViewModel = hiltViewModel()
) {
    val model by viewModel.model.collectAsState()

    LaunchedEffect(modelId) { viewModel.load(modelId) }

    val m = model ?: run {
        Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

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
                    IconButton(onClick = { /* TODO: bookmark */ }) {
                        Icon(Icons.Default.BookmarkBorder, "Bookmark")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = 20.dp, end = 20.dp,
                top = padding.calculateTopPadding() + 4.dp,
                bottom = padding.calculateBottomPadding() + 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DomainChip(label = m.domain.displayName, domain = m.domain)
                        AssistChip(
                            onClick = {},
                            label = { Text(m.difficulty.displayName, style = BernardType.LabelSmall) }
                        )
                    }
                    Text(m.name, style = BernardType.DisplayMedium, color = MaterialTheme.colorScheme.onBackground)
                    Text(m.origin, style = BernardType.LabelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            item {
                Text(m.fullDesc, style = BernardType.BodyLarge, color = MaterialTheme.colorScheme.onBackground)
            }

            if (m.whenToUse.isNotEmpty()) {
                item {
                    SectionBlock(title = "WHEN TO USE", bullets = m.whenToUse)
                }
            }

            if (m.commonMistakes.isNotEmpty()) {
                item {
                    SectionBlock(title = "COMMON MISTAKES", bullets = m.commonMistakes)
                }
            }

            if (m.pairingLogic.isNotEmpty()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            "PAIRS WELL WITH",
                            style = BernardType.LabelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(m.pairingLogic) { pairedId ->
                                AssistChip(
                                    onClick = { onRelatedModelClick(pairedId) },
                                    label = { Text(pairedId, style = BernardType.LabelSmall) }
                                )
                            }
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = { /* Test yourself */ },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Test Yourself →", style = BernardType.BodyMedium)
                }
            }
        }
    }
}

@Composable
private fun SectionBlock(title: String, bullets: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(title, style = BernardType.LabelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        bullets.forEach { bullet ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("•", style = BernardType.BodyMedium, color = BernardColors.AccentBlue)
                Text(bullet, style = BernardType.BodyMedium, color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}
