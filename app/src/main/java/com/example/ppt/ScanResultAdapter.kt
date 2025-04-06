package com.example.ppt

import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ScanResultAdapter(
    private val items: List<ScanResult>,
    private val onClickListener: ((device: ScanResult) -> Unit)
    ) : RecyclerView.Adapter<ScanResultAdapter.ViewHolder>() {



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            println("MADE IT TO SCANRESULT")
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_scan_result, parent, false)

            Log.d("ScanResultAdapter", "Items size: ${items.size}")
            return ViewHolder(view, onClickListener)
        }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    class ViewHolder(
        private val view: View,
        private val onClickListener: ((device: ScanResult) -> Unit)
    ) : RecyclerView.ViewHolder(view) {

        @SuppressLint("MissingPermission")
        fun bind(result: ScanResult) {

            view.findViewById<TextView>(R.id.device_name).text = result.device.name ?: "Unnamed"
            view.findViewById<TextView>(R.id.mac_address).text = result.device.address
            view.findViewById<TextView>(R.id.signal_strength).text = "${result.rssi} dBm"
            view.setOnClickListener { onClickListener.invoke(result) }
        }
    }
}