package com.example.simpleusbserialreveng

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment: Fragment() {

    private val viewModel: dataViewModel by activityViewModels()
    private lateinit var safeContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflate the view
        viewModel.readEmmissivLive.observe(viewLifecycleOwner, androidx.lifecycle.Observer { readEMS -> currentEmmis.text = readEMS })
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveEmmis.setOnClickListener { SaveEmmissivity() }



    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }




    private fun SaveEmmissivity()
    {
        var inputEMSString = editEmmis.text.toString()
        var inputEMSDouble = inputEMSString.toDouble()
        //check if the string is not empty and its > 0 and <= 1
        if(inputEMSString != "" && inputEMSDouble > 0 && inputEMSDouble <=1 )
        {
            viewModel.setEmmissiv(inputEMSString)
        }

        else
        {
            Toast.makeText(safeContext, "Error: Value must be > 0 and <= 1", Toast.LENGTH_SHORT).show()
        }

        //clear the text
        editEmmis.text.clear()
    }
}