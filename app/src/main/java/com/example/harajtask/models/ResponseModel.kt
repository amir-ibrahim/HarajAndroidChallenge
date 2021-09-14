package com.example.harajtask.models

import java.io.Serializable

data class HarajModel(
    val title: String,
    val username: String,
    val thumbURL: String,
    val commentCount: Int,
    val city: String,
    val date: Long,
    val body: String
) : Serializable