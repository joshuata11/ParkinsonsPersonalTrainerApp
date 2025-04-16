package com.example.ppt

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatDelegate
import com.example.ppt.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val calendar = Calendar.getInstance()
    private val currentYear = calendar.get(Calendar.YEAR)
    private val currentMonth = calendar.get(Calendar.MONTH)
    private val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val textViewDate = view.findViewById<TextView>(R.id.WorkoutInfo)

        val date = calendarView.date
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val dateCurrent = dateFormat.format(Date(date))
        textViewDate.text = "Workouts completed today $dateCurrent :"

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "${month + 1}/$dayOfMonth/$year"
            textViewDate.text = "Workouts completed on $selectedDate :"
            if(year == currentYear && month == currentMonth && dayOfMonth == currentDay){
                textViewDate.text = "Workouts completed today $selectedDate :"
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

        checkBLEPermission()



        val bleScanner = BLEScanner()














        return view
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