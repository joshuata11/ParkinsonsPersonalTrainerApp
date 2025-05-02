package com.example.ppt.fragments

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.example.ppt.PrefObject
import com.example.ppt.R
import com.example.ppt.TimerService
import com.example.ppt.bluetoothlowenergy.BLEDeviceDataO
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BikingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BikingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var file: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_biking, container, false)
        val timestamp = System.currentTimeMillis()
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val militarytime = format.format(Date(timestamp))
        val context = requireContext()

        val starter = view.findViewById<Button>(R.id.startsesBiking)
        val ender = view.findViewById<Button>(R.id.endsesBiking)
        val check = view.findViewById<CheckBox>(R.id.checkBoxBiking)
        val fileDescription = view.findViewById<TextView>(R.id.FileDescriptionBiking)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)


        if(BLEDeviceDataO.getCheckBox()){
            check.isChecked = true
            check.text = "Stop Collecting Data"
            fileDescription.isVisible = true
        }

        //this can be improved by moving it to an object so each fragments that uses the check can just call the object
        check.setOnClickListener(){
            if(check.isChecked ){
                check.text = "Stop Collecting Data"
                fileDescription.isVisible = true
                val activitytype = PrefObject.getActivity()
                val fileName = "PPT_"+activitytype+"_$militarytime.csv"
                file = File(context.getExternalFilesDir(null), fileName)
                //println(file.absoluteFile)
                file.writeText("timestamp,sensor,x,y,z\n")
                writeData.setFile(file)
                BLEDeviceDataO.setCheckBox(true)
            }else{
                check.text = "Start Collecting Data"
                fileDescription.isVisible = false
                moveFileToDownloads(requireContext(), writeData.getFile())
                BLEDeviceDataO.setCheckBox(false)
            }
        }

        starter.setOnClickListener {

            val intent = Intent(context, TimerService::class.java)
            if (!PrefObject.getSession()) {
                //if (!ongoing) {

                context.startService(intent)
                //ongoing = true
                PrefObject.setSession(true)
            }
            else{
                context.stopService(intent)

                context.startService(intent)
            }
        }

        ender.setOnClickListener {
            if (PrefObject.getSession()) {
                //if (ongoing) {
                val intent = Intent(context, TimerService::class.java)
                context.stopService(intent)
                //ongoing = false
                PrefObject.setSession(false)
            }
        }






        return  view
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun moveFileToDownloads(context: Context, sourceFile: File) {
        if (!sourceFile.exists()) {

            return
        }

        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, sourceFile.name)
            put(MediaStore.Downloads.MIME_TYPE, "text/csv")
            put(MediaStore.Downloads.RELATIVE_PATH, "Download/MySensorData")
            put(MediaStore.Downloads.IS_PENDING, 1)
        }

        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            resolver.openOutputStream(it)?.use { outputStream ->
                val inputStream = FileInputStream(sourceFile)
                inputStream.copyTo(outputStream)
                inputStream.close()
            }

            contentValues.clear()
            contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
            resolver.update(it, contentValues, null, null)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BikingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BikingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}