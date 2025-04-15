package com.example.ppt.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.ppt.R
import com.example.ppt.TimerService

/**
 * A simple [Fragment] subclass.
 * Use the [WalkingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WalkingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var ongoing = false

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
        val pageview = inflater.inflate(R.layout.fragment_walking, container, false)
        val starter = pageview.findViewById<Button>(R.id.startses)
        val ender = pageview.findViewById<Button>(R.id.endses)
        //val getter = pageview.findViewById<Button>(R.id.getses)

        starter.setOnClickListener {
            if (!ongoing) {
                val intent = Intent(context, TimerService::class.java)
                context?.startService(intent)
                ongoing = true
            }
        }

        ender.setOnClickListener {
            if (ongoing) {
                val intent = Intent(context, TimerService::class.java)
                context?.stopService(intent)
                ongoing = false
            }
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