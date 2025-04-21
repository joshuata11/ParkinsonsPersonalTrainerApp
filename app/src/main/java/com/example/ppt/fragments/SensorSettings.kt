package com.example.ppt.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.appcompat.app.AppCompatDelegate
import com.example.ppt.PrefObject
import com.example.ppt.R
import com.example.ppt.bluetoothlowenergy.BLEDeviceDataO
import com.example.ppt.bluetoothlowenergy.BLEScanner

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class SensorSettings : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var changedMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let { PrefObject.init(it) }
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings_sensor, container, false)

        val btn = view.findViewById<Button>(R.id.connectSensor)
        val btn2 = view.findViewById<Button>(R.id.vibrator)
        val disconnect = view.findViewById<Button>(R.id.disconnect)
        val goalbut = view.findViewById<Button>(R.id.goalbutton)
        val curgoal = view.findViewById<TextView>(R.id.goaltime)
        var cgoal = PrefObject.getGoal()
        curgoal.setText("$cgoal")
        val newgoal = view.findViewById<EditText>(R.id.newgoal)

        val bleScanner = BLEScanner()

        goalbut.setOnClickListener() {
            if (newgoal.text.toString().isNotEmpty()) {
                PrefObject.setGoal(newgoal.text.toString().toLong())
                cgoal = PrefObject.getGoal()
                curgoal.setText("$cgoal")
            }
        }

        btn.setOnClickListener(){



            val v = bleScanner.scanBLEDecivce()
            //println("From show device dialog" + scanResults)





            Handler(Looper.getMainLooper()).postDelayed({
                val devices = BLEDeviceDataO.getList()
                println("DEVICES" + devices)
                val deviceNames = devices.map { it.name ?: "Unnamed" }.toTypedArray()
                val checkedItems = BooleanArray(deviceNames.size)
                //val deviceMac = devices.map { it.device.address ?: "No Address" }.toTypedArray()

                //val inflater = LayoutInflater.from(requireContext())
                //val dialogview = inflater.inflate(R.layout.dialog_custom, null)

                // val macTextView = view.findViewById<TextView>(R.id.macAddress)
                //val deviceNameView = view.findViewById<TextView>(R.id.deviceTitle)

                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Select a Device")
                builder.setMultiChoiceItems(deviceNames, checkedItems){_, index, isChecked ->
                    checkedItems[index] = isChecked
                }
                    .setPositiveButton("Connect") { _, _ ->
                        for (i in checkedItems.indices) {
                            if (checkedItems[i]) {
                                bleScanner.connectToDevice(devices[i], requireContext())
                            }
                        }
                    }



                /*builder.setItems(deviceNames) { _, which ->
                    val selectedItem = devices[which].device
                    BLEDeviceDataO.setSelectedDevice(selectedItem)
                    Toast.makeText(context, "Connecting to: $selectedItem", Toast.LENGTH_SHORT).show()

                    bleScanner.connectToDevice(selectedItem, requireContext())
                }*/

                //builder.setItems(deviceNames) { dialog, which ->
                // val selectedDevice = scanResults[which]
                // Handle the device selection, e.g., start connection
                // }
                builder.setNegativeButton("Cancel", null)

                builder.show()
            },3000)


        }

        btn2.setOnClickListener(){
            bleScanner.sendVibrationCommand()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Settings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Settings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}