package com.bernardvb.ui.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bernardvb.data.repository.AnalysisRepository
import com.bernardvb.domain.model.Analysis
import com.bernardvb.domain.model.enums.MoodContext
import com.bernardvb.domain.model.enums.UrgencyContext
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    private val analysisRepository: AnalysisRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    sealed class AnalysisState {
        data object Idle : AnalysisState()
        data object Loading : AnalysisState()
        data class Success(val analysis: Analysis) : AnalysisState()
        data class Error(val message: String, val isPaywallRequired: Boolean = false) : AnalysisState()
    }

    private val _analysisState = MutableStateFlow<AnalysisState>(AnalysisState.Idle)
    val analysisState: StateFlow<AnalysisState> = _analysisState.asStateFlow()

    private val _selectedMood = MutableStateFlow<MoodContext?>(null)
    val selectedMood: StateFlow<MoodContext?> = _selectedMood.asStateFlow()

    private val _selectedUrgency = MutableStateFlow<UrgencyContext?>(null)
    val selectedUrgency: StateFlow<UrgencyContext?> = _selectedUrgency.asStateFlow()

    private val _currentAnalysis = MutableStateFlow<Analysis?>(null)
    val currentAnalysis: StateFlow<Analysis?> = _currentAnalysis.asStateFlow()

    fun setMood(mood: MoodContext?) { _selectedMood.value = mood }
    fun setUrgency(urgency: UrgencyContext?) { _selectedUrgency.value = urgency }

    fun analyse(situationText: String, isDeep: Boolean = false) {
        viewModelScope.launch {
            _analysisState.value = AnalysisState.Loading
            val result = analysisRepository.analysesSituation(
                situationText = situationText,
                mood = _selectedMood.value,
                urgency = _selectedUrgency.value,
                isDeep = isDeep,
                previousModelIds = emptyList()
            )
            result.fold(
                onSuccess = { analysis ->
                    _currentAnalysis.value = analysis
                    auth.currentUser?.uid?.let { uid ->
                        analysisRepository.saveAnalysis(analysis, uid)
                    }
                    _analysisState.value = AnalysisState.Success(analysis)
                },
                onFailure = { error ->
                    _analysisState.value = AnalysisState.Error(
                        message = error.message ?: "Analysis failed",
                        isPaywallRequired = error.message?.contains("402") == true
                    )
                }
            )
        }
    }

    fun loadAnalysis(analysisId: String) {
        viewModelScope.launch {
            val analysis = analysisRepository.getAnalysisById(analysisId)
            if (analysis != null) {
                _currentAnalysis.value = analysis
                _analysisState.value = AnalysisState.Success(analysis)
            }
        }
    }

    fun reset() {
        _analysisState.value = AnalysisState.Idle
        _currentAnalysis.value = null
        _selectedMood.value = null
        _selectedUrgency.value = null
    }
}
