package com.example.assignment.domain.repository

import com.example.assignment.data.model.Item
import com.example.assignment.domain.usecases.UseCaseResult


interface ItemRepository {
    suspend fun getItems(): UseCaseResult<List<Item>>
}
