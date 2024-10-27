package com.example.assignment.domain.usecases

import com.example.assignment.data.model.Item
import com.example.assignment.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetItemsUseCase(private val repository: ItemRepository) {
    operator fun invoke(): Flow<UseCaseResult<List<Item>>> = flow {
        emit(UseCaseResult.Loading)
        val result = repository.getItems()
        emit(result)
    }
}