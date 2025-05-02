package com.example.ppt.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.ppt.R

class UserSetup : AppCompatActivity() {
    private lateinit var pass: EditText
    private lateinit var user: EditText
    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = getSharedPreferences("account_info", MODE_PRIVATE) ?: return
        val editor = sharedPref.edit()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.setup)
        pass = findViewById(R.id.Password)
        user = findViewById(R.id.Username)
        button = findViewById(R.id.Button)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        button.setOnClickListener {
            if (user.getText().toString().isNotEmpty() && pass.getText().toString().isNotEmpty()) {

                editor.putString("userKey",user.getText().toString())
                editor.putString("passKey",pass.getText().toString())
                editor.putBoolean("setupKey",false)
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

        }


    }
}