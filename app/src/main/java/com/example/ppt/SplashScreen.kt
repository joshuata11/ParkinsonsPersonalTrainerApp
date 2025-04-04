package com.example.ppt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.ppt.databinding.ActivityMainBinding
import android.os.*

object AppSession {
    var isFirstLaunch = true

}

class SplashScreen : AppCompatActivity() {
    private var count = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = getSharedPreferences("account_info", MODE_PRIVATE) ?: return
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.splash_screen)

        Handler().postDelayed( {

            val setup = sharedPref.getBoolean("setupKey", true)
            val remember = sharedPref.getBoolean("rememberKey", false)
            val darkmode = sharedPref.getBoolean("dark", false)
            val editor = sharedPref.edit()

            println("Value of count is" + count)



            println("Value of remember from splash screen " + remember)

            /*
            val editor = sharedPref.edit()
            editor.putString("userKey","UserTest")
            editor.putString("passKey","Pass")
            editor.apply()
            */


            if (setup) {
                val intent = Intent(this, UserSetup::class.java)
                startActivity(intent)
                onDestroy()
            } else if (!remember ) {
                val intent = Intent(this, LoginScreen::class.java)
                startActivity(intent)
            } else{
                AppSession.isFirstLaunch = false
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }, 1000)

    }
}