package com.example.ppt

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.example.ppt.bluetoothlowenergy.BLEScanner
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimerService: Service(){
    private val handler = Handler(Looper.getMainLooper())
    private var timeStarted : Long = 0
    private var timeElapsed : Long = 0
    private var seconds : Long = 0
    private var counter : Long = 0
    private val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    private var dateCurrent = dateFormat.format(Date())
    private var runnable: Runnable = object : Runnable {
        override fun run() {
            timeElapsed = System.currentTimeMillis() - timeStarted
            seconds = timeElapsed/1000
            PrefObject.setTimer(seconds)
            println(PrefObject.getGoal() * 60)
            if(seconds == PrefObject.getGoal() * 60){
                BLEScanner().sendVibrationCommand()
            }
            println("$seconds seconds")
            if (seconds - counter >= 60) {
                PrefObject.setDaily(dateCurrent,1)
                counter += 60
            }
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
        PrefObject.setTimer(0)
        dateCurrent = dateFormat.format(Date())
        handler.post(runnable)
    }

    override fun onDestroy() {
        //println("CLOSING SERVICE")
        handler.removeCallbacks(runnable)
        PrefObject.setTimer(0)
        //PrefObject.setSession(false)
        super.onDestroy()
    }
}