package com.bernardvb.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bernardvb.data.repository.ModelRepository
import com.bernardvb.domain.model.MentalModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModelDetailViewModel @Inject constructor(
    private val modelRepository: ModelRepository
) : ViewModel() {

    private val _model = MutableStateFlow<MentalModel?>(null)
    val model: StateFlow<MentalModel?> = _model

    fun load(modelId: String) {
        viewModelScope.launch {
            _model.value = modelRepository.getModelById(modelId)
        }
    }
}
