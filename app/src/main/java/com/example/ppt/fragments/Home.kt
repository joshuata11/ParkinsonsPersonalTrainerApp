package com.example.ppt.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanResult
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.ppt.PrefObject
import com.example.ppt.R
import com.example.ppt.bluetoothlowenergy.BLEDeviceDataO
import com.example.ppt.bluetoothlowenergy.BLEScanner
import com.example.ppt.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomeBinding
    //private var devices = mutableListOf<ScanResult>()
    private var fragmentContext: Context? = null
    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val enableBluetoothLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d("BLE", "Bluetooth enabled")
            } else {
                Log.d("BLE", "Bluetooth not enabled by user")
            }
        }
    private val calendar = Calendar.getInstance()
    private val currentYear = calendar.get(Calendar.YEAR)
    private val currentMonth = calendar.get(Calendar.MONTH)
    private val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    private var total: Long = 0
    private var goal: Long = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val textViewDate = view.findViewById<TextView>(R.id.WorkoutInfo)
        val workoutMins = view.findViewById<TextView>(R.id.completed)

        val date = calendarView.date
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val dateCurrent = dateFormat.format(Date(date))
        total = PrefObject.getDaily(dateCurrent)
        goal = PrefObject.getGoal()
        if (total < goal) {
            textViewDate.text = "You haven't completed your workout for $dateCurrent"
            workoutMins.text = "$total out of $goal"
        }    else {
            textViewDate.text = "You've completed your workout for $dateCurrent"
            workoutMins.text = "$total"
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            var selectedDate = "${month + 1}/$dayOfMonth/$year"
            if (month < 9) {
                selectedDate = "0${month + 1}/$dayOfMonth/$year"
            }
            total = PrefObject.getDaily(selectedDate)
            if (total < goal) {
                textViewDate.text = "You haven't completed your workout for $selectedDate"
                workoutMins.text = "$total out of $goal"
            }    else {
                textViewDate.text = "You've completed your workout for $selectedDate"
                workoutMins.text = "$total"
            }
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = requireActivity().getSharedPreferences("Mode", Context.MODE_PRIVATE)
        val darkmode = sharedPreferences.getBoolean("dark", false)


        if(darkmode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        context?.let { PrefObject.init(it) }
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        /*val context = requireContext()
        val fileName = "sensor_data.csv"
        val file = File(context.getExternalFilesDir(null), fileName)
        file.writeText("timestamp,sensor,x,y,z")*/

        checkBLEPermission()
        requestBLEenable()


        return view
    }

    fun switchFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment).commit()
        }
    }

    private fun checkBLEPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12+ (API 31+)
            if (requireContext().checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                requireContext().checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                requireContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {

                requestPermissions(
                    arrayOf(
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.ACCESS_FINE_LOCATION

                    ),
                    1 // Request Code
                )
            }
        }
    }

    private fun requestBLEenable() {
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            enableBluetoothLauncher.launch(enableBtIntent)
        }
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}