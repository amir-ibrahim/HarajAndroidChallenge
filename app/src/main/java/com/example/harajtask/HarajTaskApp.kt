package com.example.harajtask

import android.app.Dialog
import androidx.multidex.MultiDexApplication
import okhttp3.OkHttpClient
import timber.log.Timber

class HarajTaskApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        private lateinit var appInstance: HarajTaskApp

        fun getAppInstance(): HarajTaskApp {
            return appInstance
        }
    }
}

