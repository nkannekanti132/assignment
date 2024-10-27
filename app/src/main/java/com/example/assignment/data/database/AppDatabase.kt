package com.example.assignment.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment.data.model.ItemEntity

@Database(entities = [ItemEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
