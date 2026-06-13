package com.bernardvb.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bernardvb.data.repository.ModelRepository
import com.bernardvb.domain.model.MentalModel
import com.bernardvb.domain.model.enums.Domain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LibraryUiState(
    val models: List<MentalModel> = emptyList(),
    val searchQuery: String = "",
    val selectedDomain: Domain? = null,
    val isPro: Boolean = false,
    val isLoading: Boolean = true,
    val showPaywall: Boolean = false
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val modelRepository: ModelRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _selectedDomain = MutableStateFlow<Domain?>(null)
    private val _isPro = MutableStateFlow(false)
    private val _showPaywall = MutableStateFlow(false)

    val uiState: StateFlow<LibraryUiState> = combine(
        _searchQuery
            .debounce(200)
            .flatMapLatest { query ->
                if (query.isBlank()) modelRepository.getAllModels()
                else modelRepository.searchModels(query)
            },
        _selectedDomain,
        _isPro,
        _showPaywall
    ) { models, domain, isPro, showPaywall ->
        val filtered = if (domain != null) models.filter { it.domain == domain } else models
        LibraryUiState(
            models = filtered,
            searchQuery = _searchQuery.value,
            selectedDomain = domain,
            isPro = isPro,
            isLoading = false,
            showPaywall = showPaywall
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LibraryUiState())

    fun onSearchQueryChange(query: String) { _searchQuery.value = query }
    fun onDomainSelected(domain: Domain?) { _selectedDomain.value = domain }
    fun onLockedModelTapped() { _showPaywall.value = true }
    fun dismissPaywall() { _showPaywall.value = false }

    init {
        viewModelScope.launch { modelRepository.seedInitialModels() }
    }
}
