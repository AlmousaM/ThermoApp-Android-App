package com.example.simpleusbserialreveng

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class HistoryFragment: Fragment() {

    private lateinit var temp: TempHistoryData



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        temp = TempHistoryData()
    }


    //here we inflate the views and get saved data from onSavedInctance if we want
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //todo: inflate the xml view
        val view = inflater.inflate(R.layout.activity_main, container, false)
        //todo: get views here "view.findViewByID"
        return view
    }


    //set up listener here to avoid triigering them on rotation
    override fun onStart() {
        super.onStart()
    }
}


