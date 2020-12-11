package com.example.simpleusbserialreveng

import androidx.lifecycle.ViewModel
import java.util.*


//view model to hold a list of tempreture reading
class TempListViewModel: ViewModel() {

    val tempReadings  = mutableListOf<TempHistoryData>()

    init {
        for (i in 0 until 100) {
            val temp = TempHistoryData()
            temp.title = "Reading #$i"
            temp.tempreture = (i+3).toString() + " C"
            tempReadings += temp
        }
    }


}