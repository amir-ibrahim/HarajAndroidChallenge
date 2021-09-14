package com.example.harajtask.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harajtask.repository.HarajRepository

class HarajModelProviderFactory(
    val app: Application,
    val harajRepository: HarajRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HarajViewModel(app, harajRepository) as T
    }
}