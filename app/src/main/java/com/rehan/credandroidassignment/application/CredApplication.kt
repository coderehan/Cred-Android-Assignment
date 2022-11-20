package com.rehan.credandroidassignment.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CredApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}