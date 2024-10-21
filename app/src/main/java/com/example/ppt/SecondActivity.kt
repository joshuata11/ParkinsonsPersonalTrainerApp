package com.example.ppt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


private  const val TAG = "MainActivity"
class SecondActivity : AppCompatActivity()  {
    private  lateinit var button2: Button
    private lateinit var text: TextView
    private lateinit var webview: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        button2 = findViewById(R.id.backButton)
        text = findViewById(R.id.Test)
        webview = findViewById(R.id.WW)


       //webview.loadUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
        val total = intent.getStringExtra("Total")
        text.text = total




        button2.setOnClickListener( View.OnClickListener
        {
            startActivity(Intent(this@SecondActivity, MainActivity::class.java))
        })

    }
}