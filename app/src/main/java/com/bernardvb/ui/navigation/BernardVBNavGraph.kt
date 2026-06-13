package com.bernardvb.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bernardvb.ui.analysis.AnalysisResultScreen
import com.bernardvb.ui.analysis.DecisionPathsScreen
import com.bernardvb.ui.home.HomeScreen
import com.bernardvb.ui.journal.JournalScreen
import com.bernardvb.ui.learn.LearnScreen
import com.bernardvb.ui.library.LibraryScreen
import com.bernardvb.ui.library.ModelDetailScreen
import com.bernardvb.ui.profile.ProfileScreen

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
            com.bernardvb.ui.home.SituationInputScreen(
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
                onSaveToJournal = { navController.navigate(Screen.Journal.route) },
                onDrillDown = { navController.navigate(Screen.DrillDown.route(id)) }
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

        composable(Screen.Journal.route) {
            JournalScreen(
                onEntryClick = { entryId -> navController.navigate(Screen.JournalDetail.route(entryId)) }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onThinkingDNA = { navController.navigate(Screen.ThinkingDNA.route) }
            )
        }
    }
}
