package com.saadi.stickfigure

import android.app.Application
import com.google.firebase.FirebaseApp
import com.saadi.stickfigure.utils.NetworkConnectivityService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StickFigureApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //init wifiService
        NetworkConnectivityService.instance.initializeWithApplicationContext(this)

        //init firebase
        FirebaseApp.initializeApp(this)

    }

}