package com.example.ppt

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


private  const val TAG = "MainActivity"
private const val INITAL_TIP = 15
class MainActivity : AppCompatActivity() {
    private lateinit var baseAmount: EditText
    private lateinit var seekBar: SeekBar
    private lateinit var tipPercent: TextView
    private lateinit var tipAmount: TextView
    private lateinit var total: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        baseAmount = findViewById(R.id.BaseAmount)
        seekBar = findViewById(R.id.SeekBarTip)
        tipPercent = findViewById(R.id.Percent)
        tipAmount = findViewById(R.id.tipTotal)
        total = findViewById(R.id.TotalValue)

        seekBar.progress = INITAL_TIP
        tipPercent.text = "$INITAL_TIP%"
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanges $progress")
                tipPercent.text = "$progress%"
                    computeTipandTotal()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        baseAmount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipandTotal()
            }
        })






    }

    private fun computeTipandTotal() {

        if (baseAmount.text.isEmpty()){
            tipAmount.text = ""
            total.text = ""
            return
        }
        val baseamount = baseAmount.text.toString().toDouble()
        val tipPercent = seekBar.progress

        val tipamount = baseamount * tipPercent / 100
        val totalA = baseamount + tipamount

        tipAmount.text = "%.2f".format(tipamount)
        total.text = "%.2f".format(totalA)


    }
}