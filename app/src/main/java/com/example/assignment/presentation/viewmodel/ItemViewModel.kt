package com.example.assignment.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.data.model.Item
import com.example.assignment.domain.usecases.GetItemsUseCase
import com.example.assignment.domain.usecases.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UseCaseResult<List<Item>>>(UseCaseResult.Loading)
    val uiState: StateFlow<UseCaseResult<List<Item>>> = _uiState

    private val _listIds = MutableStateFlow<List<Int>>(emptyList()) // To store unique listIds
    val listIds: StateFlow<List<Int>> = _listIds

    private var allItems: List<Item> = emptyList() // To store all fetched items
    private var currentSearchQuery: String = ""
    private var selectedListId: Int? = null // To track selected listId

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch(Dispatchers.IO) { // Use IO dispatcher for network calls
            getItemsUseCase().collect { result ->
                when (result) {
                    is UseCaseResult.Success -> {
                        allItems = result.data.filter { !it.name.isNullOrBlank() } // Filter out items with null/blank names
                        _listIds.value = allItems.map { it.listId }.distinct() // Extract unique listIds
                        Log.d("ttt",_listIds.value.toString())
                        _uiState.value = UseCaseResult.Success(filterItems(currentSearchQuery, selectedListId)) // Update UI state with filtered items
                    }
                    is UseCaseResult.Error -> _uiState.value = result
                    is UseCaseResult.Loading -> _uiState.value = UseCaseResult.Loading
                }
            }
        }
    }

    // Method to handle search query changes
    fun onSearchQueryChanged(query: String) {
        currentSearchQuery = query
        _uiState.value = UseCaseResult.Success(filterItems(query, selectedListId)) // Update UI state with new filtered items
    }

    // Method to handle listId selection changes
    fun onListIdSelected(listId: Int?) {
        selectedListId = listId
        _uiState.value = UseCaseResult.Success(filterItems(currentSearchQuery, selectedListId)) // Update UI state with filtered items
    }

    // Helper function to filter items based on the search query and selected listId
    private fun filterItems(query: String, listId: Int?): List<Item> {
        return allItems.filter {
            (listId == null || it.listId == listId) &&
                    (query.isEmpty() || it.name?.contains(query, ignoreCase = true) == true)
        }
    }
}
