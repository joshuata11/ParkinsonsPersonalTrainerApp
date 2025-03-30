package com.example.ppt

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.ppt.databinding.ActivityMainBinding

class LoginScreen : AppCompatActivity() {
    private lateinit var pass: EditText
    private lateinit var user: EditText
    private lateinit var button: Button
    private lateinit var rem: CheckBox
    private lateinit var badlogin: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = getSharedPreferences("account_info", MODE_PRIVATE) ?: return
        val editor = sharedPref.edit()
        var remember = false
        var rememberPref = sharedPref.getBoolean("rememberKey", false)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_screen)
        pass = findViewById(R.id.Pass)
        user = findViewById(R.id.Name)
        button = findViewById(R.id.Login)
        rem = findViewById(R.id.Remember)
        badlogin = findViewById(R.id.badLogin)


        if(rememberPref){
            remember = true
        }else
        {
            remember = false
        }

        rem.setOnCheckedChangeListener { buttonView, isChecked ->
            remember = isChecked
        }

        button.setOnClickListener {
            val UserN = sharedPref.getString("userKey", "")
            val PassW = sharedPref.getString("passKey", "")
            if (user.getText().toString() == UserN && pass.getText().toString() == PassW) {

                if (remember) {
                    editor.putBoolean("rememberKey", true)
                    editor.apply()
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
            else{
                badlogin.isVisible = true
            }


        }
    }
}