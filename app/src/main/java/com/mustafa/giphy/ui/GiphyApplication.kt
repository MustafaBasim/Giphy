package com.mustafa.giphy.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GiphyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}