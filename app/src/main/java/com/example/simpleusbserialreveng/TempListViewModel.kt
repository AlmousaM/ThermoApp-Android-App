package com.example.simpleusbserialreveng

import android.app.Activity
import android.content.Context
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File
import java.time.LocalDateTime
import java.util.*


//view model to hold a list of tempreture reading
class TempListViewModel: ViewModel() {

    val TempReadingsLiveData =  MutableLiveData<MutableList<TempHistoryData>>()
    val tempReadingList = mutableListOf<TempHistoryData>()



    fun getTempReadings(folderNAme: File)
    {

        //clear the list
        tempReadingList.clear()



        //get list of files in the folder
        val files = folderNAme.listFiles()

        //loop thru the files to get the name and the image URI
        for (i in files.indices) {
            val imageFile = files[i]
            var tempDataObject = TempHistoryData()

            //split the file name by delimeter
            var splitedName = imageFile.name.split("_")

            tempDataObject.title = (i+1).toString()
            tempDataObject.date = splitedName[0]
            tempDataObject.tempreture = splitedName[2]
            tempDataObject.photoPath = imageFile.path
            tempReadingList += tempDataObject
        }


        TempReadingsLiveData.value = tempReadingList

    }



//    init {
//        for (i in 0 until 100) {
//            val temp = TempHistoryData()
//            temp.title = "Reading #$i"
//            temp.tempreture = (i+3).toString() + " C"
//            tempReadings += temp
//        }
//    }


}