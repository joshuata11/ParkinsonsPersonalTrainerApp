package com.example.ppt

import android.bluetooth.le.ScanResult

object BLEDeviceDataO{
    private val scanResultslist = mutableListOf<ScanResult>()






    fun setList(scanResults: MutableList<ScanResult>) {

        scanResultslist.clear()
        scanResultslist.addAll(scanResults)
        println(scanResultslist)

    }




    fun getList(): MutableList<ScanResult> {
        println("from get list" + scanResultslist)
        return scanResultslist
    }

}

class BLEDeviceData(){
    private val scanResultslist = mutableListOf<ScanResult>()






    fun setList(scanResults: MutableList<ScanResult>) {

        scanResultslist.clear()
        scanResultslist.addAll(scanResults)
        println(scanResultslist)

    }




    fun getList(): MutableList<ScanResult> {
        println("from get list" + scanResultslist)
        return scanResultslist
    }




}

