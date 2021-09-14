package com.example.harajtask.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.harajtask.HarajTaskApp
import com.example.harajtask.models.HarajModel
import com.example.harajtask.repository.HarajRepository
import com.example.harajtask.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class HarajViewModel(
    app: Application,
    private val harajRepository: HarajRepository
) : AndroidViewModel(app) {

    val harajItems: MutableLiveData<Resource<ArrayList<HarajModel>>> = MutableLiveData()
    var harajItemsPage = 1
    var harajItemsResponse: ArrayList<HarajModel>? = null

    init {
        getHaragItems()
    }

    /**
     * Haraj Items
     * **/
    fun getHaragItems() = viewModelScope.launch {
        safeHarajItemsCall()
    }

    private suspend fun safeHarajItemsCall() {
        harajItems.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = harajRepository.getHaragListItems()
                harajItems.postValue(handleHarajItemsResponse(response))
            } else {
                harajItems.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> harajItems.postValue(Resource.Error("Network Failure"))
                else -> harajItems.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleHarajItemsResponse(response: Response<ArrayList<HarajModel>>): Resource<ArrayList<HarajModel>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                harajItemsPage++
                if (harajItemsResponse == null) {
                    harajItemsResponse = resultResponse
                } else {
                    val oldHarajItems = harajItemsResponse
                    oldHarajItems?.addAll(resultResponse)
                }
                return Resource.Success(harajItemsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<HarajTaskApp>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}
