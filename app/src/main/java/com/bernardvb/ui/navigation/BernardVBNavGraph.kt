package com.bernardvb.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bernardvb.ui.analysis.AnalysisResultScreen
import com.bernardvb.ui.analysis.DecisionPathsScreen
import com.bernardvb.ui.analysis.DrillDownScreen
import com.bernardvb.ui.home.HomeScreen
import com.bernardvb.ui.home.SituationInputScreen
import com.bernardvb.ui.journal.JournalDetailScreen
import com.bernardvb.ui.journal.JournalScreen
import com.bernardvb.ui.learn.LearnScreen
import com.bernardvb.ui.learn.LearningPathScreen
import com.bernardvb.ui.library.LibraryScreen
import com.bernardvb.ui.library.ModelDetailScreen
import com.bernardvb.ui.paywall.PaywallScreen
import com.bernardvb.ui.profile.ProfileScreen
import com.bernardvb.ui.profile.ThinkingDNAScreen

@Composable
fun BernardVBNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onAnalyseClick = { navController.navigate(Screen.SituationInput.route) },
                onAnalysisClick = { id -> navController.navigate(Screen.AnalysisResult.route(id)) }
            )
        }

        composable(Screen.SituationInput.route) {
            SituationInputScreen(
                onBack = { navController.popBackStack() },
                onAnalysisReady = { id ->
                    navController.navigate(Screen.AnalysisResult.route(id)) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

        composable(Screen.AnalysisResult.route) { backStack ->
            val id = backStack.arguments?.getString("analysisId") ?: return@composable
            AnalysisResultScreen(
                analysisId = id,
                onBack = { navController.popBackStack() },
                onDecisionPaths = { navController.navigate(Screen.DecisionPaths.route(id)) },
                onModelClick = { modelId -> navController.navigate(Screen.ModelDetail.route(modelId)) }
            )
        }

        composable(Screen.DecisionPaths.route) { backStack ->
            val id = backStack.arguments?.getString("analysisId") ?: return@composable
            DecisionPathsScreen(
                analysisId = id,
                onBack = { navController.popBackStack() },
                onSaveToJournal = {
                    navController.navigate(Screen.Journal.route) {
                        popUpTo(Screen.Home.route)
                    }
                },
                onDrillDown = { navController.navigate(Screen.DrillDown.route(id)) }
            )
        }

        composable(Screen.DrillDown.route) { backStack ->
            val id = backStack.arguments?.getString("analysisId") ?: return@composable
            DrillDownScreen(
                analysisId = id,
                onBack = { navController.popBackStack() },
                onModelClick = { modelId -> navController.navigate(Screen.ModelDetail.route(modelId)) }
            )
        }

        composable(Screen.Library.route) {
            LibraryScreen(
                onModelClick = { modelId -> navController.navigate(Screen.ModelDetail.route(modelId)) }
            )
        }

        composable(Screen.ModelDetail.route) { backStack ->
            val modelId = backStack.arguments?.getString("modelId") ?: return@composable
            ModelDetailScreen(
                modelId = modelId,
                onBack = { navController.popBackStack() },
                onRelatedModelClick = { id -> navController.navigate(Screen.ModelDetail.route(id)) }
            )
        }

        composable(Screen.Learn.route) {
            LearnScreen(
                onModelClick = { modelId -> navController.navigate(Screen.ModelDetail.route(modelId)) },
                onPathClick = { pathId -> navController.navigate(Screen.LearningPath.route(pathId)) }
            )
        }

        composable(Screen.LearningPath.route) { backStack ->
            val pathId = backStack.arguments?.getString("pathId") ?: return@composable
            LearningPathScreen(
                pathId = pathId,
                onBack = { navController.popBackStack() },
                onModelClick = { modelId -> navController.navigate(Screen.ModelDetail.route(modelId)) }
            )
        }

        composable(Screen.Journal.route) {
            JournalScreen(
                onEntryClick = { entryId -> navController.navigate(Screen.JournalDetail.route(entryId)) }
            )
        }

        composable(Screen.JournalDetail.route) { backStack ->
            val entryId = backStack.arguments?.getString("entryId") ?: return@composable
            JournalDetailScreen(
                entryId = entryId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onThinkingDNA = { navController.navigate(Screen.ThinkingDNA.route) }
            )
        }

        composable(Screen.ThinkingDNA.route) {
            ThinkingDNAScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Paywall.route) { backStack ->
            val trigger = backStack.arguments?.getString("trigger") ?: "general"
            PaywallScreen(
                trigger = trigger,
                onDismiss = { navController.popBackStack() }
            )
        }
    }
}
