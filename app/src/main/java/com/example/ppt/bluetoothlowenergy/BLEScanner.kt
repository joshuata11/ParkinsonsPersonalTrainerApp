package com.example.ppt.bluetoothlowenergy

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import com.example.ppt.fragments.Home
import com.example.ppt.sensor_enums.PPT_LL
import com.example.ppt.sensor_enums.PPT_LW
import com.example.ppt.sensor_enums.PPT_RL
import com.example.ppt.sensor_enums.PPT_RW
import java.util.UUID

class BLEScanner {

    var scanning = false
    private lateinit var home: Home
    private var nextDescriptorToWrite: BluetoothGattDescriptor? = null
    private var gatt: BluetoothGatt? = null
    private var imuServiceUUID: UUID? = null
    private var lwaccelerometerUUID: UUID? = null
    private var lwgyroscopeCharUUID: UUID? = null
    private var lwvibrationUUID: UUID? = null
    private var rwaccelerometerUUID: UUID? = null
    private var rwgyroscopeCharUUID: UUID? = null
    private var rwvibrationUUID: UUID? = null
    private var llaccelerometerUUID: UUID? = null
    private var llgyroscopeCharUUID: UUID? = null
    private var llvibrationUUID: UUID? = null
    private var rlaccelerometerUUID: UUID? = null
    private var rlgyroscopeCharUUID: UUID? = null
    private var rlvibrationUUID: UUID? = null


    private val scanResults = mutableListOf<BluetoothDevice>()

    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        //val leDeviceListAdapter = BLEDeviceAdapter()
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val indexQuery = scanResults.indexOfFirst { result.device.address == result.device.address }

            val device = result.device
            if(!scanResults.contains(device)){
                scanResults.add(device)
            }

            BLEDeviceDataO.setList(scanResults)

        }

        override fun onScanFailed(errorCode: Int) {
            Log.e("ScanCallback", "onScanFailed: code $errorCode")
        }
    }


    val connectedDevices = mutableListOf<BluetoothDevice>()
    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice, context: Context){
        if(!connectedDevices.contains(device)){
            connectedDevices.add(device)
        }
         gatt =  device.connectGatt(context, false, object : BluetoothGattCallback(){
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



                val targetNames = setOf("PPT_LW", "PPT_RW", "PPT_LL", "PPT_RL")

                connectedDevices.forEach { device ->
                    val name = device.name
                    if (name != null && name in targetNames) {
                        // Handle each matched device name
                        when (name) {
                            "PPT_LW" -> {
                                imuServiceUUID = PPT_LW.ACCELEROMETER.serviceUUID//UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
                                lwaccelerometerUUID = PPT_LW.ACCELEROMETER.characteristicUUID//UUID.fromString("19b10000-e8f2-537e-4f6c-d104768a1215")
                                lwgyroscopeCharUUID = PPT_LW.GYROSCOPE.characteristicUUID
                                lwvibrationUUID = PPT_LW.VIBRATION.serviceUUID

                            }

                            "PPT_RW" -> {
                                imuServiceUUID = PPT_RW.ACCELEROMETER.serviceUUID//UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
                                rwaccelerometerUUID = PPT_RW.ACCELEROMETER.characteristicUUID//UUID.fromString("19b10000-e8f2-537e-4f6c-d104768a1215")
                                rwgyroscopeCharUUID = PPT_RW.GYROSCOPE.characteristicUUID
                                rwvibrationUUID = PPT_RW.VIBRATION.serviceUUID
                            }

                            "PPT_LL" -> {
                                imuServiceUUID = PPT_LL.ACCELEROMETER.serviceUUID//UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
                                llaccelerometerUUID = PPT_LL.ACCELEROMETER.characteristicUUID//UUID.fromString("19b10000-e8f2-537e-4f6c-d104768a1215")
                                llgyroscopeCharUUID = PPT_LL.GYROSCOPE.characteristicUUID
                                llvibrationUUID = PPT_LL.VIBRATION.serviceUUID
                            }

                            "PPT_RL" -> {
                                imuServiceUUID = PPT_RL.ACCELEROMETER.serviceUUID//UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
                                rlaccelerometerUUID = PPT_RL.ACCELEROMETER.characteristicUUID//UUID.fromString("19b10000-e8f2-537e-4f6c-d104768a1215")
                                rlgyroscopeCharUUID = PPT_RL.GYROSCOPE.characteristicUUID
                                rlvibrationUUID = PPT_RL.VIBRATION.serviceUUID
                            }
                        }
                    }
                }




                //UUID.fromString("19b10000-e8f2-537e-4f6c-d104768a1216")
                val descriptorUUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

                val service = gatt?.getService(imuServiceUUID)
                val accelChar = service?.getCharacteristic(lwaccelerometerUUID)
                val gyroChar = service?.getCharacteristic(lwgyroscopeCharUUID)





                if (gatt != null && accelChar != null) {
                    gatt.setCharacteristicNotification(accelChar, true)
                    val acelDesc = accelChar.getDescriptor(descriptorUUID)
                    if (acelDesc != null) {
                        acelDesc.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                    }


                    gatt.setCharacteristicNotification(gyroChar, true)
                    val gyroDesc = gyroChar?.getDescriptor(descriptorUUID)
                    if (gyroDesc != null) {
                        gyroDesc.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                    }
                    gatt.writeDescriptor(acelDesc)
                    nextDescriptorToWrite = gyroDesc

                }


                println("Services discovered for device: " + device.name)

            }

            override fun onDescriptorWrite(
                gatt: BluetoothGatt?,
                descriptor: BluetoothGattDescriptor?,
                status: Int
            ) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    // If there's another descriptor waiting, write it next
                    nextDescriptorToWrite?.let {
                        gatt?.writeDescriptor(it)
                        nextDescriptorToWrite = null

                    }
                } else {
                    println("Descriptor write failed with status: $status")
                }
            }


            override fun onCharacteristicChanged(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?
            ) {
                val uuid = characteristic?.uuid.toString()
                val value = characteristic?.value?.toString(Charsets.UTF_8)
                /*if (gatt != null) {
                    sendVibrationCommand()
                }*/

                when (uuid) {

                    PPT_LW.ACCELEROMETER.characteristicUUID.toString() -> { // Accelerometer
                        val parts = value?.split(",")
                        if (parts != null) {
                            if (parts.size == 3) {
                                val x = parts?.get(0)?.toFloatOrNull()
                                val y = parts.get(1)?.toFloatOrNull()
                                val z = parts.get(2)?.toFloatOrNull()
                                println("Accelerometer for device " + connectedDevices[0].name +": X: $x, Y: $y, Z: $z")
                            }
                        }
                    }


                    PPT_LW.GYROSCOPE.characteristicUUID.toString() -> { // Gyroscope
                        val parts = value?.split(",")
                        if (parts != null) {
                            if (parts.size == 3) {
                                val x = parts[0].toFloatOrNull()
                                val y = parts[1].toFloatOrNull()
                                val z = parts[2].toFloatOrNull()
                                println("Gyroscope for device " + connectedDevices[0].name +": X: $x, Y: $y, Z: $z")
                            }
                        }
                    }
            }

        }


    })
    }


    var alreadysent = 0
    val byte = ByteArray(1)
    @SuppressLint("MissingPermission")
    fun sendVibrationCommand() {

        //UUID.fromString("19B10000-E8F2-537E-4F6C-D104768A1214")
        val characteristicUUID = PPT_LW.VIBRATION.characteristicUUID//UUID.fromString("19B10001-E8F2-537E-4F6C-D104768A1214")

        val service = gatt?.getService(lwvibrationUUID)
        if (service == null) {
            println("Service is NULL")
            return
        }

        val characteristic = service.getCharacteristic(characteristicUUID)
        if (characteristic == null) {
            println("Characteristic is NULL")
            return
        }
        println("INSIDE SEND VIBRATION")
        val byteVal = booleanToByteArray(true)
        characteristic.value = byteVal


          //  if (alreadysent == 0) {
             //   Handler(Looper.getMainLooper()).postDelayed({
                    println("SENDING VIBRATION")
                    val result = gatt?.writeCharacteristic(characteristic)
                    alreadysent = 1

            //    }, 50)
           // }


    }

    fun booleanToByteArray(value: Boolean): ByteArray {
        return byteArrayOf(if (value) 1 else 0)
    }




    @SuppressLint("MissingPermission")
    fun scanBLEDecivce() {

        var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        /*al enableBluetoothLauncher =  { result ->
            if (result.resultCode == RESULT_OK) {
                println("BT enabled")
            } else {
                println("BT DIsabled")
            }
        }*/


        home = Home()
        val bleScanner: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(!bleScanner.isEnabled){
            println("BT IS DISABLED")
            val enable = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)


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


            val handler = Handler()

            if (!scanning) {
                handler.postDelayed({
                scanning = false
                println("Context before: $this" )

                blescanner.stopScan(leScanCallback)
                }, 3000)
                scanning = true
                println("Scanning...")
                blescanner.startScan(leScanCallback)
                println("Finsihed scan")
            } else {
                scanning = false
                blescanner.stopScan(leScanCallback)
            }
            return
        }

    }

}