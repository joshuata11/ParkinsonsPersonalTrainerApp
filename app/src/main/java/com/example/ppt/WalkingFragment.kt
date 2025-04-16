package com.example.ppt

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WalkingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WalkingFragment : Fragment() {

    public var temp: Long = -2

    // TODO: Rename and change types of parameters
    private var br: TimerReceiver? = null
    private var param1: String? = null
    private var param2: String? = null
    private var ongoing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let { PrefObject.init(it) }

        br = TimerReceiver()

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter("com.TIMER_UPDATE")
        context?.let { registerReceiver(it, br, filter, RECEIVER_EXPORTED) }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val pageview = inflater.inflate(R.layout.fragment_walking, container, false)
        val starter = pageview.findViewById<Button>(R.id.startses)
        val ender = pageview.findViewById<Button>(R.id.endses)
        val getter = pageview.findViewById<Button>(R.id.valuebut)

        starter.setOnClickListener {

            if (!PrefObject.getSession()) {
            //if (!ongoing) {
                val intent = Intent(context, TimerService::class.java)
                context?.startService(intent)
                //ongoing = true
                PrefObject.setSession(true)
            }
        }

        ender.setOnClickListener {
            if (PrefObject.getSession()) {
            //if (ongoing) {
                val intent = Intent(context, TimerService::class.java)
                context?.stopService(intent)
                //ongoing = false
                PrefObject.setSession(false)
            }
        }

        getter.setOnClickListener {
            temp = br?.getData()!!
            println("$temp was received")
        }

        return pageview
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScrollingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WalkingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
