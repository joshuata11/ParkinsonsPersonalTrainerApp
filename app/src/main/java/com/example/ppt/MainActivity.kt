package com.example.ppt

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.ppt.databinding.ActivityMainBinding
import java.util.Calendar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bluetoothAdapter: BluetoothAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("Mode", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val darkmode = sharedPreferences.getBoolean("dark", false)
        println("Executing on create in main")

       if(darkmode){
           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        //checkBLEPermission()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())



        binding.bottomNavigationView.setOnItemSelectedListener{

            when(it.itemId){
                R.id.home -> replaceFragment(Home())
                R.id.workout -> replaceFragment(Workout())
                R.id.settings -> replaceFragment(Settings())


            }
                true

        }


    }

    fun checkBLEPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12+ (API 31+)
            if (this@MainActivity.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                this@MainActivity.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                this@MainActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {

                requestPermissions(
                    arrayOf(
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.ACCESS_FINE_LOCATION

                    ),
                    1 // Request Code
                )
            }
        }
    }


    // Device scan callback.
    /*private val leScanCallback: ScanCallback = object : ScanCallback() {
        val leDeviceListAdapter = BLEDeviceAdapter()
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            leDeviceListAdapter.addDevice(result.device)
            //leDeviceListAdapter.notifyDataSetChanged()
        }

        override fun onScanFailed(errorCode: Int) {
            println("BLE FAILED WITH ERROR CODE:" + errorCode)
        }
    }*/




    /*@SuppressLint("MissingPermission")
    fun scanBLEDecivce() {

        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val enableBluetoothLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                println("BT enabled")
            } else {
                println("BT DIsabled")
            }
        }


        println("made it here")
        val bleScanner: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        println("Made it here")
        if(!bleScanner.isEnabled){
            println("BT IS DIABLED")
            val enable = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            enableBluetoothLauncher.launch(enable)
        }
        *//*if(!isBluetoothLeSupported()){
            return
        }*//*
        else{
            println("MADE IT TO ELSE")
            val blescanner = bluetoothAdapter.bluetoothLeScanner

            if(blescanner == null){
                println("DEVICE DOES NOT SUPPORT BLE")
                return
            }

            var scanning = false
            val handler = Handler()

            if (!scanning) {
                //handler.postDelayed({
                scanning = false
                println("Context before: $this" )

                blescanner.stopScan(leScanCallback)
                //}, 10000)
                scanning = true
                println("Scanning...")
                blescanner.startScan(leScanCallback)
                println("Finsihed scan")
            } else {
                scanning = false
                //blescanner.stopScan(leScanCallback)
            }
            return
        }

    }*/

     fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }

}