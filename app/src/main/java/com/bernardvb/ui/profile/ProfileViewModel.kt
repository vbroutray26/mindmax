package com.bernardvb.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bernardvb.data.repository.UserProgressRepository
import com.bernardvb.domain.model.ThinkerLevel
import com.bernardvb.domain.model.UserProgress
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class ProfileUiState(
    val displayName: String = "",
    val totalXP: Int = 0,
    val currentLevel: ThinkerLevel = ThinkerLevel.CURIOUS_MIND,
    val currentStreak: Int = 0,
    val totalAnalyses: Int = 0,
    val totalChallenges: Int = 0
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userProgressRepository: UserProgressRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    val progress: StateFlow<UserProgress?> = userProgressRepository
        .getProgress(auth.currentUser?.uid ?: "")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val uiState: StateFlow<ProfileUiState> = userProgressRepository
        .getProgress(auth.currentUser?.uid ?: "")
        .map { progress ->
            ProfileUiState(
                displayName = auth.currentUser?.displayName ?: "Thinker",
                totalXP = progress?.totalXP ?: 0,
                currentLevel = progress?.currentLevel ?: ThinkerLevel.CURIOUS_MIND,
                currentStreak = progress?.currentStreak ?: 0,
                totalAnalyses = progress?.totalAnalyses ?: 0,
                totalChallenges = progress?.totalChallengesCompleted ?: 0
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileUiState())
}
