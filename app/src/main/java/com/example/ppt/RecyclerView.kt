/*
package com.example.ppt

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.ppt.databinding.FragmentRecyclerViewBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

*/
/**
 * A simple [Fragment] subclass.
 * Use the [RecyclerView.newInstance] factory method to
 * create an instance of this fragment.
 *//*

class RecyclerView : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRecyclerViewBinding
    private var devices = mutableListOf<ScanResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
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
        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)

        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        //showDeviceSelectionDialog(requireContext())
        setupRecyclerView(binding, requireContext())

        return view
    }


    @SuppressLint("MissingPermission")
    fun showDeviceSelectionDialog(context: Context) {


        println(devices)
        val deviceNames = devices.map { it.device.name ?: "Unnamed" }.toTypedArray()

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select a Device")
        builder.setItems(deviceNames) { dialog, which ->
            val selectedDevice = devices[which]
            // Handle the device selection, e.g., start connection
        }
        builder.setNegativeButton("Cancel", null)

        builder.show()
    }


    fun recieveList(scanResults: MutableList<ScanResult>) {
        //println("FROM RECEIVE" + scanResults)
        devices.clear()
        devices.addAll(scanResults)
    }


    */
/*private fun setupRecyclerView(binding: FragmentRecyclerViewBinding, context: Context) {
        println("SETTING UP RECYCLER")
        binding.scanResultsRecyclerView.apply{
            adapter = BLEScanner().scanResultAdapter
            layoutManager = LinearLayoutManager(
                context,
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }

        val animator = binding.scanResultsRecyclerView.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }*//*


    companion object {
        */
/**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecyclerView.
         *//*

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecyclerView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}*/
