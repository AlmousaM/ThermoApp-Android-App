package com.example.simpleusbserialreveng

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class dataViewModel: ViewModel() {


    var objectTemp: String = ""
    //live object tempreture
    val liveObjTemp = MutableLiveData<String>()

    //live emmissivity data sent to terminal fragment to set the sensor emmissivity
     val setEmmissivLive = MutableLiveData<String>()

    //data read from the temp sensor
    val readEmmissivLive = MutableLiveData<String>()

    fun setLiveObjTemp(temp: String) {
        liveObjTemp.value = temp
    }

    fun setEmmissiv(emmissiv: String) {
        setEmmissivLive.value = emmissiv
    }

    fun getSensorEmmissiv(data: String) {
        readEmmissivLive.value = data
    }




    init {
        liveObjTemp.value = " "
        setEmmissivLive.value = " "
        readEmmissivLive.value = " "

    }

}