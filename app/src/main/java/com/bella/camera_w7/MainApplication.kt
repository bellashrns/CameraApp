package com.bella.camera_w7

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}