package com.example.ppt.bluetoothlowenergy

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.hardware.Sensor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object BLEDeviceDataO{
    private val scanResultslist = mutableListOf<BluetoothDevice>()
    private var selectedDevice: BluetoothDevice? = null
    private var gatt: BluetoothGatt? = null
    private var accelList = mutableListOf<Float>()
    private var gyroList = mutableListOf<Float>()
    private lateinit var sensor: String
    private var checkBox: Boolean = false
    val csvBuffer = StringBuilder()

    fun setList(scanResults: MutableList<BluetoothDevice>) {

        scanResultslist.clear()
        scanResultslist.addAll(scanResults)
        println(scanResultslist)

    }

    fun getList(): MutableList<BluetoothDevice> {
        println("from get list" + scanResultslist)
        return scanResultslist
    }

    fun setSelectedDevice(device: BluetoothDevice) {
        selectedDevice = device
    }

    fun getSelectedDevice(): BluetoothDevice? {
        return selectedDevice
    }

    fun setGatt(gattD: BluetoothGatt){
        gatt = gattD
    }

    fun getGatt(): BluetoothGatt? {
        return gatt
    }

    fun setAccelList(x: Float?, y: Float?, z: Float?) {
        x?.let { accelList.add(it) }
        y?.let { accelList.add(it) }
        z?.let { accelList.add(it) }
    }

    fun getAccellist(): List<Float> {
        return accelList
    }

    fun setGyroList(x: Float?, y: Float?, z: Float?) {
        x?.let { gyroList.add(it) }
        y?.let { gyroList.add(it) }
        z?.let { gyroList.add(it) }
    }

    fun getGyrolist(): List<Float> {
        return gyroList
    }

    fun setSensorName(name: String) {
        sensor = name
    }

    fun getSensorName(): String {
        return sensor
    }

    fun setCheckBox(b: Boolean) {
        checkBox = b
    }

    fun getCheckBox(): Boolean {
        return checkBox
    }








    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice, context: Context){
        val gatt =  device.connectGatt(context, false, object : BluetoothGattCallback(){
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                if(newState == BluetoothProfile.STATE_CONNECTED){
                    println("Connected to device: " + device.name)
                    if (gatt != null) {
                        gatt.discoverServices()
                    }

                }else if(newState == BluetoothProfile.STATE_DISCONNECTED){
                    println("Disconnected from device: " + device.name)
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                println("Services discovered for device: " + device.name)
            }
        })
    }


}


