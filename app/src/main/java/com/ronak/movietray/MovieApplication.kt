package com.ronak.movietray;

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class MovieApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }

}

