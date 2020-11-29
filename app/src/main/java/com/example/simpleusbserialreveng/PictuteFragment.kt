package com.example.simpleusbserialreveng

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class PictuteFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("pictureFragment", "onCreateView called")
        return inflater.inflate(R.layout.fragment_picture, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()

        //get the data from serial port
        Log.d("pictureFragment", "onDestroy called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("pictureFragment", "onDetached called")
    }
}