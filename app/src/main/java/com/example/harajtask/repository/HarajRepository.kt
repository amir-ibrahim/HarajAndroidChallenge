package com.example.harajtask.repository

import com.example.harajtask.api.RetrofitInstance

class HarajRepository {
    suspend fun getHaragListItems() = RetrofitInstance.api.getHarajList()
}