package com.bernardvb.ui.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bernardvb.domain.model.enums.Domain
import com.bernardvb.ui.components.DomainChip
import com.bernardvb.ui.components.ModelCard
import com.bernardvb.ui.theme.BernardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    onModelClick: (String) -> Unit,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Search bar
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = viewModel::onSearchQueryChange,
            onSearch = {},
            active = false,
            onActiveChange = {},
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("Search 250+ mental models…", style = BernardType.BodyMedium) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        ) {}

        // Domain filter chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            item {
                FilterChip(
                    selected = uiState.selectedDomain == null,
                    onClick = { viewModel.onDomainSelected(null) },
                    label = { Text("All", style = BernardType.LabelSmall) }
                )
            }
            items(Domain.entries) { domain ->
                DomainChip(
                    label = domain.displayName,
                    domain = domain,
                    isSelected = uiState.selectedDomain == domain,
                    onClick = { viewModel.onDomainSelected(domain) }
                )
            }
        }

        // Model list
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.models, key = { it.id }) { model ->
                ModelCard(
                    model = model,
                    isLocked = !model.isFree && !uiState.isPro,
                    onClick = {
                        if (model.isFree || uiState.isPro) onModelClick(model.id)
                        else viewModel.onLockedModelTapped()
                    }
                )
            }
        }
    }
}
