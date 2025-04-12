package com.example.ppt

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.example.ppt.databinding.FragmentHomeBinding
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
    private lateinit var binding: FragmentHomeBinding
    //private var devices = mutableListOf<ScanResult>()
    private var fragmentContext: Context? = null




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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val context = requireContext()

        checkBLEPermission()
        val bleScanner = BLEScanner()

        var scanning = false


        val btn = view.findViewById<Button>(R.id.BTBTN)



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











        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Save the context when the fragment is attached
        fragmentContext = context
    }

    override fun onDetach() {
        super.onDetach()
        // Clear the context when the fragment is detached to avoid memory leaks
        fragmentContext = null
    }

    fun useContext(): Context {
        // You can safely use fragmentContext here, but always check for null
        fragmentContext?.let { context ->
           return requireContext()
        }
        return requireContext()
    }

    /*override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the devices list in a bundle
        outState.putParcelableArrayList("devices", ArrayList(devices))
    }*/

   /* // Restore the list state after the fragment is recreated
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            val restoredDevices = it.getParcelableArrayList<ScanResult>("devices")
            restoredDevices?.let { list ->
                devices.clear()
                devices.addAll(list)
            }
        }
    }

    fun recieveList(scanResults: MutableList<ScanResult>) {
        //println("FROM RECEIVE" + scanResults)
        //devices.clear()
        devices.addAll(scanResults)

        println("after receive" + devices)
    }

    fun getList(): MutableList<ScanResult> {
        println(devices)
        return devices
    }*/

    @SuppressLint("MissingPermission")
    fun showDeviceSelectionDialog(scanResults: MutableList<ScanResult>) {

        viewLifecycleOwner.lifecycleScope.launch {
            delay(3000) // Delay for 3 seconds


            println("From show device dialog" + scanResults)
            val deviceNames = scanResults.map { it.device.name ?: "Unnamed" }.toTypedArray()
            val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6")

            //
            // val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, items)

            val builder = AlertDialog.Builder(fragmentContext)
            builder.setTitle("Select a Device")
            builder.setItems(deviceNames) { dialog, which ->
                val selectedItem = deviceNames[which]
                Toast.makeText(context, "You chose: $selectedItem", Toast.LENGTH_SHORT).show()
            }

            //builder.setItems(deviceNames) { dialog, which ->
            // val selectedDevice = scanResults[which]
            // Handle the device selection, e.g., start connection
            // }
            builder.setNegativeButton("Cancel", null)

            builder.show()
        }


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