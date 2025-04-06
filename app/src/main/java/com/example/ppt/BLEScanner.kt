package com.example.ppt

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.os.Handler
import android.util.Log

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


///*
//            andler(Looper.getMainLooper()).post{
//                home.recieveList(scanResults)
//            }H*/




            //println(scanResults)
            //home.showDeviceSelectionDialog(scanResults)




            //leDeviceListAdapter.notifyDataSetChanged()
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e("ScanCallback", "onScanFailed: code $errorCode")
        }
    }

    /* fun setupRecyclerView(binding: FragmentHomeBinding, context: Context) {
         println("SETTING UP RECYCLER")
        binding.scanResultsRecyclerView.apply{
            adapter = scanResultAdapter
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }

        val animator = binding.scanResultsRecyclerView.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }*/


    fun getList(): MutableList<ScanResult> {
        println("from blescan" + scanResults)
        while(scanResults.isEmpty()){

        }
        return scanResults
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