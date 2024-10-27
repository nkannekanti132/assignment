package com.example.assignment.data.respository

import android.util.Log
import com.example.assignment.data.database.ItemDao
import com.example.assignment.data.model.Item
import com.example.assignment.data.network.FetchApiService
import com.example.assignment.domain.repository.ItemRepository
import com.example.assignment.domain.usecases.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val apiService: FetchApiService,
    private val itemDao: ItemDao
) : ItemRepository {

    private val cacheExpiryDuration = 24 * 60 * 60 * 1000 // 24 hours in milliseconds

    override suspend fun getItems(): UseCaseResult<List<Item>> {
        return try {
            val currentTime = System.currentTimeMillis()

            // Check if cached items are available and fresh
            val cachedItems = itemDao.getAllItems().map { it.toNetworkModel() }
            if (cachedItems.isNotEmpty()) {
                val lastUpdated = itemDao.getLastUpdatedTime()
                if (currentTime - lastUpdated!! < cacheExpiryDuration) {
                    // Use cached data if it's fresh
                    return UseCaseResult.Success(cachedItems)
                }
            }

            // Fetch from API
            val apiItems = withContext(Dispatchers.IO) {
                apiService.getItems()
                    .filter { !it.name.isNullOrBlank() }
                    .sortedWith(compareBy({ it.listId }, { extractNumber(it.name) }))
            }

            // Cache API data to database
            withContext(Dispatchers.IO) {
                itemDao.clearAll()
                itemDao.insertAll(apiItems.map { it.toEntity() })
                itemDao.updateLastUpdatedTime(currentTime) // Update the last updated timestamp
            }

            // Retrieve from database
            val newCachedItems = itemDao.getAllItems().map { it.toNetworkModel() }
            UseCaseResult.Success(apiItems)
        } catch (e: Exception) {
            // Fallback to database if API call fails
            val cachedItems = itemDao.getAllItems().map { it.toNetworkModel() }
            if (cachedItems.isNotEmpty()) {
                UseCaseResult.Success(cachedItems)
            } else {
                UseCaseResult.Error("Failed to load items: Error")
            }
        }
    }
}

private fun extractNumber(input: String?): Int? {
    return input?.let { Regex("\\d+").find(it)?.value?.toInt() }
}
