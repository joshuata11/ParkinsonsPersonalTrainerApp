package com.example.ppt

import android.content.Intent
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

class LoginScreen : AppCompatActivity() {
    private lateinit var pass: EditText
    private lateinit var user: EditText
    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_screen)
        pass = findViewById(R.id.Pass)
        user = findViewById(R.id.Name)
        button = findViewById(R.id.Login)

        button.setOnClickListener {
            if (user.getText().toString().isNotEmpty() && pass.getText().toString().isNotEmpty()) {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

        }


    }
}