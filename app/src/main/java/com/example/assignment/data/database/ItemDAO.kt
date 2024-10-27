package com.example.assignment.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment.data.model.ItemEntity


@Dao
interface ItemDao {
    @Query("SELECT * FROM items ORDER BY listId, name")
    fun getAllItems(): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<ItemEntity>)

    @Query("DELETE FROM items")
    fun clearAll()

    // Add a query to retrieve the last updated timestamp
    @Query("SELECT lastUpdated FROM items LIMIT 1") // Assumes lastUpdated is a field in ItemEntity
    fun getLastUpdatedTime(): Long?

    // Add a method to update the last updated timestamp
    @Query("UPDATE items SET lastUpdated = :timestamp")
    fun updateLastUpdatedTime(timestamp: Long)
}
