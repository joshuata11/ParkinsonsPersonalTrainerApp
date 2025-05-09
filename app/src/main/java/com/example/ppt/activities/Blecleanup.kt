package com.example.ppt.activities

import android.app.Application
import android.content.ComponentCallbacks2
import com.example.ppt.bluetoothlowenergy.BLEScanner

class Blecleanup : Application(), ComponentCallbacks2 {

    lateinit var bleManager: BLEScanner

    override fun onCreate() {
        super.onCreate()
        bleManager = BLEScanner()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            // App is going to background (swipe away, etc.)
            bleManager.disconnectGattServer()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        bleManager.disconnectGattServer()
    }

    override fun onTerminate() {
        super.onTerminate()
        bleManager.disconnectGattServer() // Only on emulators usually
    }
}