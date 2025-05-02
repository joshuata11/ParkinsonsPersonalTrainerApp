package com.example.ppt.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.appcompat.app.AppCompatDelegate
import com.example.ppt.PrefObject
import com.example.ppt.R
import com.example.ppt.activities.LoginScreen
import com.example.ppt.activities.MainActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountSettings : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings_account, container, false)

        val logoutbtn = view.findViewById<Button>(R.id.logout)
        val sharedPreferences = requireActivity().getSharedPreferences("Mode", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val darkmode = sharedPreferences.getBoolean("dark", false)
        var isdarkmodeon = false
        val curuser = view.findViewById<TextView>(R.id.username)
        curuser.setText(PrefObject.getUsername())
        val newuser = view.findViewById<EditText>(R.id.newUsername)
        val userbtn = view.findViewById<Button>(R.id.changeuser)
        val oldpass = view.findViewById<EditText>(R.id.oldpass)
        val newpass = view.findViewById<EditText>(R.id.newpass)
        val passbtn = view.findViewById<Button>(R.id.changepass)

        userbtn.setOnClickListener() {
            if (newuser.getText().toString().isNotEmpty()) {
                val newU = newuser.getText().toString()
                PrefObject.setUsername(newU)
                curuser.setText(PrefObject.getUsername())
            }
        }

        passbtn.setOnClickListener() {
            if (oldpass.getText().toString().isNotEmpty() && newpass.getText().toString().isNotEmpty() && oldpass.getText().toString() == PrefObject.getPassword()) {
                val newP = newpass.getText().toString()
                PrefObject.setPassword(newP)
            }
        }

        logoutbtn.setOnClickListener(){
            PrefObject.setRemember(false)
            val intent = Intent(this@AccountSettings.requireContext(), LoginScreen::class.java)
            println("Value of remember key is" + sharedPreferences.getBoolean("rememberKey", false))
            startActivity(intent)
        }

        /*if(darkmode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            isdarkmodeon = true
            themebtn.setText("Enable Light Mode")


        }
        else{
            isdarkmodeon = false
            themebtn.setText("Enable Dark Mode")
        }


        themebtn.setOnClickListener(){
            if(isdarkmodeon){
                requireActivity().window.setWindowAnimations(R.style.WindowAnimationFade)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("dark", false)
                editor.apply()
                themebtn.setText("Enable Dark Mode")
                isdarkmodeon = false
                val intent = Intent(this@AccountSettings.requireContext(), MainActivity::class.java)
                startActivity(intent)



            }
            else {
                requireActivity().window.setWindowAnimations(R.style.WindowAnimationFade)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("dark", true)
                editor.apply()
                themebtn.setText("Disable Dark Mode")
                isdarkmodeon = true
                val intent = Intent(this@AccountSettings.requireContext(), MainActivity::class.java)
                startActivity(intent)


            }

        }*/

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