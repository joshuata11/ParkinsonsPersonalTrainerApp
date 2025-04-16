package com.example.ppt.fragments

import com.example.ppt.bluetoothlowenergy.BLEDeviceDataO.csvBuffer
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object writeData {
    private lateinit var file: File




    fun setFile(_file: File){
        file = _file
    }

    fun getFile(): File {
        return file
    }

    fun writeSensorDataToCsv(sensorType: String, x: Float?, y: Float?, z: Float?) {
        //file.writeText("timestamp,sensor,x,y,z\n")
        val timestamp = System.currentTimeMillis()
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val militarytime = format.format(Date(timestamp))
        val dataLine = buildString {
            append("$militarytime, $sensorType")
            append("$x,  $y , $z ")
            append("\n")
        }

        file.appendText(dataLine)
    }


    fun bufferSensorData(sensorType: String, x: Float?, y: Float?, z: Float?) {
        val timestamp = System.currentTimeMillis()
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val militaryTime = format.format(Date(timestamp))

        val dataLine = "$militaryTime,$sensorType,$x,$y,$z\n"
        csvBuffer.append(dataLine)
    }



}