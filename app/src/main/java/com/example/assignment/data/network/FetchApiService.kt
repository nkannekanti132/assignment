package com.example.assignment.data.network

import com.example.assignment.data.model.Item
import retrofit2.http.GET


interface FetchApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}