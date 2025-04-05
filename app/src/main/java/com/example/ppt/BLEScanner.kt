package com.example.ppt

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat

class BLEScanner {

    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        val leDeviceListAdapter = BLEDeviceAdapter()
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            leDeviceListAdapter.addDevice(result.device)
            //leDeviceListAdapter.notifyDataSetChanged()
        }

        override fun onScanFailed(errorCode: Int) {
            println("BLE FAILED WITH ERROR CODE:" + errorCode)
        }
    }




    @SuppressLint("MissingPermission")
    fun scanBLEDecivce() {

        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        /*val enableBluetoothLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                println("BT enabled")
            } else {
                println("BT DIsabled")
            }
        }*/


        println("made it here")
        val bleScanner: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        println("Made it here")
        if(!bleScanner.isEnabled){
            println("BT IS DISABLED")
            val enable = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            //enableBluetoothLauncher.launch(enable)
        }
        /*if(!isBluetoothLeSupported()){
            return
        }*/
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
                handler.postDelayed({
                scanning = false
                println("Context before: $this" )

                blescanner.stopScan(leScanCallback)
                }, 10000)
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

    }

}