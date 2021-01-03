package com.example.fight_tracking


import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources


class FlightApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
        appContext = getApplicationContext()

    }

    companion object {
        private val TAG = FlightApplication::class.java.simpleName

        var appContext: Context? = null
            private set

        var application: Application? = null
            private set

        val appResources: Resources
            get() = FlightApplication.appContext!!.resources

        val appAssetManager: AssetManager
            get() = FlightApplication.appContext!!.assets
    }
}