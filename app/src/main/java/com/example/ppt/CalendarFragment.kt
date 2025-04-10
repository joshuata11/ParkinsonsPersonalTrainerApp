package com.example.ppt

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.ppt.databinding.CalendarViewBinding
import java.util.Calendar

//TODO: create the usages of the calendar
//TODO: store the data in a csv or some other file

class CalendarFragment : Fragment() {

    private lateinit var binding: CalendarFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = CalendarViewBindinginding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.calendarView)){ v, insets ->
            val ststemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

        }
        binding.calendarView.setOnDateChangeListener{_, year, month, day ->
            val date = ("%02d".format(day) + "-" + "%02d".format((month+1)) + "-" + year)

            binding.textView.text = date


    }
}