package com.bernardvb.ui.paywall

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

private data class ProFeature(val emoji: String, val title: String, val description: String)

private val proFeatures = listOf(
    ProFeature("∞", "Unlimited Analyses", "Free tier includes 5 analyses/month. Pro removes the limit."),
    ProFeature("🧠", "76 Pro Models", "Unlock advanced models: Cynefin, OODA, Bayesian Reasoning, and more."),
    ProFeature("⚡", "Deep Synthesis", "Drill down into layered analysis with actionable next steps."),
    ProFeature("📋", "Full Journal", "Unlimited journal entries with automatic check-in scheduling."),
    ProFeature("🧬", "Thinking DNA", "Your full cognitive profile: strengths, gaps, and growth trajectory."),
    ProFeature("📱", "Home Screen Widget", "Model of the Day widget — a thinking prompt every morning.")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaywallScreen(
    trigger: String = "general",
    onDismiss: () -> Unit,
    onSubscribe: () -> Unit = {}
) {
    val triggerMessage = when (trigger) {
        "analysis_limit" -> "You've used your 5 free analyses this month."
        "pro_model" -> "This model is part of Bernard/VB Pro."
        "deep_synthesis" -> "Deep Synthesis is a Pro feature."
        "thinking_dna" -> "Thinking DNA is part of Bernard/VB Pro."
        else -> "Upgrade to unlock the full Bernard/VB experience."
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Dismiss")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = 24.dp, end = 24.dp,
                top = padding.calculateTopPadding() + 8.dp,
                bottom = 40.dp
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("B", style = BernardType.DisplayLarge, color = BernardColors.AccentBlue)
                    Text(
                        "BERNARD/VB\nPRO",
                        style = BernardType.DisplayMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        triggerMessage,
                        style = BernardType.BodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    proFeatures.forEach { feature ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                feature.emoji,
                                style = BernardType.BodyLarge,
                                modifier = Modifier.width(28.dp),
                                textAlign = TextAlign.Center
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(feature.title, style = BernardType.BodyMedium, color = MaterialTheme.colorScheme.onBackground)
                                Text(feature.description, style = BernardType.BodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }

            item {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            }

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onSubscribe,
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(containerColor = BernardColors.AccentBlue)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Text("START PRO — £7.99 / month", style = BernardType.BodyMedium)
                            Text("Cancel anytime", style = BernardType.LabelSmall, color = BernardColors.AccentBlue.copy(alpha = 0.7f))
                        }
                    }
                    OutlinedButton(
                        onClick = onSubscribe,
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Text("£59.99 / year", style = BernardType.BodyMedium)
                            Text("Save 37%", style = BernardType.LabelSmall, color = BernardColors.Gold)
                        }
                    }
                    TextButton(onClick = onDismiss) {
                        Text("Maybe later", style = BernardType.BodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            item {
                Text(
                    "Prices are in GBP. Subscription auto-renews unless cancelled at least 24 hours before the end of the period. Managed by Google Play.",
                    style = BernardType.LabelSmall,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
