package com.example.ppt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.ppt.PrefObject
import com.example.ppt.R

/**
 * A simple [Fragment] subclass.
 * Use the [Workout.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class Workout : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let{PrefObject.init(it)}
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

        val view = inflater.inflate(R.layout.fragment_workout, container, false)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        val walkingbtn = view.findViewById<ImageButton>(R.id.Walkingbtn)

        val bikingbtn = view.findViewById<ImageButton>(R.id.BikingBtn)

        val lsvtbtn = view.findViewById<ImageButton>(R.id.LSVTBtn)

        val restingbtn = view.findViewById<ImageButton>(R.id.restingbtn)



        walkingbtn.setOnClickListener{
            PrefObject.setActivity("Walking")
            switchFragment(WalkingFragment())
        }
        bikingbtn.setOnClickListener{
            PrefObject.setActivity("Biking")
            switchFragment(BikingFragment())
        }
        lsvtbtn.setOnClickListener{
            println("LSVT BTN CLICKED")
            PrefObject.setActivity("LSVT")
            switchFragment(LSVTFragment())
        }
        restingbtn.setOnClickListener{
            println("RESTING BTN CLICKED")
            PrefObject.setActivity("Resting")
            switchFragment(RestingFragment())
        }
        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Workout.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Workout().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

    fun switchFragment(fragment: Fragment){
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment ).commit()
    }
        }









}