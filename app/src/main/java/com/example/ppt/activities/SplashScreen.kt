package com.example.ppt.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.os.*
import androidx.appcompat.app.AppCompatDelegate
import com.example.ppt.R

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
            /*val sharedPreferences = getSharedPreferences("Mode", Context.MODE_PRIVATE)
            val darkmode = sharedPreferences.getBoolean("dark", false)


            if(darkmode){
                println("DARK MODE ON")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }*/

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
                //onDestroy()
            } else if (!remember ) {
                val intent = Intent(this, LoginScreen::class.java)
                startActivity(intent)
            } else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }, 1000)

    }
}