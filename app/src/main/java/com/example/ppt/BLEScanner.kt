package com.example.ppt

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
import java.util.UUID

class BLEScanner {

    var scanning = false
    private lateinit var home: Home
    //private lateinit var binding: FragmentHomeBinding


    private val scanResults = mutableListOf<ScanResult>()
    val scanResultAdapter: ScanResultAdapter by lazy {
        ScanResultAdapter(scanResults) { result ->
            /*if (scanning) {
                //stopBleScan()
            }
            with(result.device) {
                //("Connecting to $address")
            //    ConnectionManager.connect(this, this@MainActivity)
            }*/
        }
    }

    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        //val leDeviceListAdapter = BLEDeviceAdapter()
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val indexQuery = scanResults.indexOfFirst { it.device.address == result.device.address }
            if(indexQuery != -1){

                println("item already in list")

            }else{
                with(result.device){
                    Log.i("ScanCallback", "Found BLE device! Name: ${name ?: "Unnamed"}, address: $address")

                }
                scanResults.add(result)
                //scanResultAdapter.notifyItemChanged(scanResults.size - 1)
                Log.i("BLEScanner", "Added new device: ${result.device.name ?: "Unnamed"}, Address: ${result.device.address}")

            }

            BLEDeviceDataO.setList(scanResults)

        }

        override fun onScanFailed(errorCode: Int) {
            Log.e("ScanCallback", "onScanFailed: code $errorCode")
        }
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


                val imuServiceUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
                val accelerometerUUID = UUID.fromString("19b10000-e8f2-537e-4f6c-d104768a1215")
                val gyroscopeCharUUID = UUID.fromString("19b10000-e8f2-537e-4f6c-d104768a1216")
                val descriptorUUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

                val service = gatt?.getService(imuServiceUUID)
                val accelChar = service?.getCharacteristic(accelerometerUUID)
                val gyroChar = service?.getCharacteristic(gyroscopeCharUUID)






                if (gatt != null && accelChar != null) {
                    gatt.setCharacteristicNotification(accelChar, true)
                    val acelDesc = accelChar?.getDescriptor(descriptorUUID)
                    if (acelDesc != null) {
                        acelDesc.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                    }
                    gatt.writeDescriptor(acelDesc)
                }

                if (gatt != null && gyroChar != null) {
                    gatt.setCharacteristicNotification(gyroChar, true)
                    val gyroDesc = accelChar?.getDescriptor(descriptorUUID)
                    if (gyroDesc != null) {
                        gyroDesc.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                    }
                    gatt.writeDescriptor(gyroDesc)
                }





                //val result = gatt?.readCharacteristic(characteristic)


                println("Services discovered for device: " + device.name)

            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic,
                status: Int
            ) {
                if(status == BluetoothGatt.GATT_SUCCESS){
                    val value = characteristic.value
                    val stringValue = value.toString(Charsets.UTF_8)


                    val xyzVals = stringValue.split(",")
                    if(xyzVals.size == 3){
                        val x = xyzVals[0].toFloatOrNull()
                        val y = xyzVals[1].toFloatOrNull()
                        val z = xyzVals[2].toFloatOrNull()

                        println("DATA READ FROM DEVICE: X: " + x +" Y: " + y +" Z: " + z )
                    }


                }
            }

            override fun onCharacteristicChanged(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?
            ) {
                val uuid = characteristic?.uuid.toString()
                val value = characteristic?.value?.toString(Charsets.UTF_8)

                when (uuid) {
                    "19b10000-e8f2-537e-4f6c-d104768a1215" -> { // Accelerometer
                        val parts = value?.split(",")
                        if (parts != null) {
                            if (parts.size == 3) {
                                val x = parts?.get(0)?.toFloatOrNull()
                                val y = parts.get(1)?.toFloatOrNull()
                                val z = parts.get(2)?.toFloatOrNull()
                                println("ACCELEROMETER: X: $x, Y: $y, Z: $z")
                            }
                        }
                    }

                    "19b10000-e8f2-537e-4f6c-d104768a1216" -> { // Gyroscope
                        val parts = value?.split(",")
                        if (parts != null) {
                            if (parts.size == 3) {
                                val x = parts[0].toFloatOrNull()
                                val y = parts[1].toFloatOrNull()
                                val z = parts[2].toFloatOrNull()
                                println("GYROSCOPE: X: $x, Y: $y, Z: $z")
                            }
                        }
                    }
            }

        }
    })
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
        println("made it here")
        val bleScanner: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        println("Made it here")
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