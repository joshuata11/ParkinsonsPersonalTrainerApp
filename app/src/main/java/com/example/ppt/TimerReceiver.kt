package com.example.ppt

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

public final class TimerReceiver : BroadcastReceiver() {

    private var data: Long = -1

    override fun onReceive(context: Context?, intent: Intent?) {
        data = intent?.getLongExtra("com.TIME", 0)!!;
        println("received time $data")
    }
}