package com.example.assignment.presentation.ui


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.assignment.data.model.Item


@Composable
fun ItemList(item: List<Item>) {
    LazyColumn {
        items(item){ item ->
                ItemRow(item = item)
        }
    }
}