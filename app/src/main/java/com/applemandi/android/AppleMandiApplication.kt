package com.applemandi.android

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppleMandiApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        Log.d("AppleMandiApplication","onCreate")
    }
}