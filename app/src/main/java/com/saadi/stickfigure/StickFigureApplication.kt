package com.saadi.stickfigure

import android.app.Application
import com.saad.homeflix.utils.NetworkConnectivityService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StickFigureApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //init wifiService
        NetworkConnectivityService.instance.initializeWithApplicationContext(this)

    }

}