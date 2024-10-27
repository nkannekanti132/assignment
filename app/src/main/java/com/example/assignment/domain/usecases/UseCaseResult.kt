package com.example.assignment.domain.usecases

sealed class UseCaseResult<out T> {
    object Loading : UseCaseResult<Nothing>()
    data class Success<out T>(val data: T) : UseCaseResult<T>()
    data class Error(val message: String) : UseCaseResult<Nothing>()
}