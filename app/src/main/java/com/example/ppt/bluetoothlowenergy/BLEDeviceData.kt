package com.example.ppt.bluetoothlowenergy

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.content.Context

object BLEDeviceDataO{
    private val scanResultslist = mutableListOf<BluetoothDevice>()
    private var selectedDevice: BluetoothDevice? = null

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


