package com.example.assignment.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.assignment.domain.usecases.UseCaseResult
import com.example.assignment.presentation.viewmodel.ItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: ItemViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val listIds by viewModel.listIds.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedListId by remember { mutableStateOf<Int?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Row to hold dropdown and search bar
        Row(modifier = Modifier.fillMaxWidth()) {
            // Dropdown for List IDs
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.weight(0.2f) // 20% width
            ) {
                OutlinedTextField(
                    value = selectedListId?.toString() ?: "-",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("ID") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                )

                // Dropdown menu
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth() // Ensure dropdown matches width
                ) {
                    if (listIds.isNotEmpty()) {
                        listIds.forEach { id ->
                            DropdownMenuItem(
                                text = { Text(id.toString()) },
                                onClick = {
                                    selectedListId = id
                                    viewModel.onListIdSelected(id)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    viewModel.onSearchQueryChanged(query)
                },
                label = { Text("Search") },
                modifier = Modifier
                    .weight(0.8f) // 80% width
                    .padding(start = 8.dp) // Optional padding between dropdown and search
            )
        }

        // Content based on UI state
        when (uiState) {
            is UseCaseResult.Loading -> Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
            is UseCaseResult.Error -> Text("Error: ${(uiState as UseCaseResult.Error).message}")
            is UseCaseResult.Success -> {
                val items = (uiState as UseCaseResult.Success).data
                ItemList(item = items)
            }
        }
    }
}


