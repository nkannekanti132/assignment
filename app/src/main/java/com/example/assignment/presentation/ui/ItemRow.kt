package com.example.assignment.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assignment.data.model.Item


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemRow(item: Item) {
    // State to manage dialog visibility
    var showDialog by remember { mutableStateOf(false) }

    // Long press modifier to show the dialog
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = {  },
                onLongClick = { showDialog = true }
            ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("List ID: ${item.listId}", style = MaterialTheme.typography.bodySmall)
            Text("Name: ${item.name}", style = MaterialTheme.typography.bodyLarge)
        }
    }

    // Show dialog if showDialog is true
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Item Details") },
            text = {
                Column {
                    Text("ID: ${item.id}")
                    Text("List ID: ${item.listId}")
                    Text("Name: ${item.name ?: "No Name"}")
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}
