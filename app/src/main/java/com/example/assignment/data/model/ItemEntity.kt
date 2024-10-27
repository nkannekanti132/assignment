package com.example.assignment.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id: Int,
    val listId: Int,
    val name: String?,
    val lastUpdated: Long
) {
    fun toNetworkModel() = Item(id, listId, name)
}


