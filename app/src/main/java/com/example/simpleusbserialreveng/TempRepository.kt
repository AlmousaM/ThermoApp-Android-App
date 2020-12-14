package com.example.simpleusbserialreveng

import android.content.Context

class TempRepository private constructor(context: Context)
{
    companion object {
        private var INSTANCE: TempRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TempRepository(context)
            }
        }
    }

    //todo: add functions to read pictures from the folder ThermoApp and parse the data into a list




    fun get(): TempRepository {
        return INSTANCE ?: throw IllegalStateException("TempRepository must be initilized")

    }
}