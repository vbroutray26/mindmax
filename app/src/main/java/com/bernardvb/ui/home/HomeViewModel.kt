package com.bernardvb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bernardvb.data.repository.AnalysisRepository
import com.bernardvb.data.repository.UserProgressRepository
import com.bernardvb.domain.model.Analysis
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val userName: String? = null,
    val currentStreak: Int = 0,
    val recentAnalyses: List<Analysis> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val analysisRepository: AnalysisRepository,
    private val userProgressRepository: UserProgressRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val userId get() = auth.currentUser?.uid ?: ""

    val uiState: StateFlow<HomeUiState> = combine(
        analysisRepository.getRecentAnalyses(userId, limit = 10),
        userProgressRepository.getProgress(userId)
    ) { analyses, progress ->
        HomeUiState(
            userName = auth.currentUser?.displayName?.split(" ")?.firstOrNull(),
            currentStreak = progress?.currentStreak ?: 0,
            recentAnalyses = analyses
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())
}
