package com.bernardvb.ui.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bernardvb.data.repository.JournalRepository
import com.bernardvb.domain.model.JournalEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val journalRepository: JournalRepository
) : ViewModel() {

    val entries: StateFlow<List<JournalEntry>> = journalRepository.getAllEntries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dueCheckIns: StateFlow<List<JournalEntry>> = journalRepository.getDueCheckIns()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
