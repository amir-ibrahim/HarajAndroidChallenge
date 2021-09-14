package com.example.harajtask.api

import com.example.harajtask.models.HarajModel
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("data.json")
    suspend fun getHarajList(): Response<ArrayList<HarajModel>>
}