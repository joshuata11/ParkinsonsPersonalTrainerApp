package com.example.ppt

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import kotlinx.coroutines.*

class TimerService: Service(){
    private val handler = Handler(Looper.getMainLooper())
    private var timeStarted : Long = 0
    private var timeElapsed : Long = 0
    private var seconds : Long = 0
    private var runnable: Runnable = object : Runnable {
        override fun run() {
            timeElapsed = System.currentTimeMillis() - timeStarted
            seconds = timeElapsed/1000
            println(seconds.toString() + " seconds")
            val intent = Intent("TIMER_UPDATE")
            intent.putExtra("time", timeElapsed)
            sendBroadcast(intent)
            // Repeat every .9 second
            handler.postDelayed(this, 900)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startCoroutineTimer()
        return START_STICKY
    }

    private fun startCoroutineTimer() {
        timeStarted = System.currentTimeMillis()
        handler.post(runnable)
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }
}