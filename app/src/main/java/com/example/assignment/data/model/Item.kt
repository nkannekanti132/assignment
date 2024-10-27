package com.example.assignment.data.model

data class Item(
    val id: Int,
    val listId: Int,
    val name: String?


){
    fun toEntity(updatedTime:Long=System.currentTimeMillis()) = ItemEntity(id, listId, name,updatedTime )
}