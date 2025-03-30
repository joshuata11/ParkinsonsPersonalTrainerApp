package com.example.ppt

import android.bluetooth.BluetoothDevice

class BLEDeviceAdapter {

    private val devices = mutableListOf<BluetoothDevice>()



    // Add a new device to the list and notify adapter
    fun addDevice(device: BluetoothDevice) {
        if (!devices.contains(device)) {
            println("MADE IT TO ADD DEVICE")
            devices.add(device)
            println("DEVICE ID: " + device)
        }
    }

    // Clear the device list (useful for when starting a new scan)
    fun clearDevices() {
        devices.clear()
    }
}