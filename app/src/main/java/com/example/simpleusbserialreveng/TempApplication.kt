package com.example.simpleusbserialreveng

import android.app.Application


//used to initilize a TempRepository instance when the app starts
class TempApplication: Application()

{
    override fun onCreate() {
        super.onCreate()
        TempRepository.initialize(this)
    }
}