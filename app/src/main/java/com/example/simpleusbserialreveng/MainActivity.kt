package com.example.simpleusbserialreveng

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager


class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.addOnBackStackChangedListener(this)
        if (savedInstanceState == null) supportFragmentManager.beginTransaction()
            .add(R.id.fragment, DeviceFragment(), "devices").commit()
        else onBackStackChanged()
    }

    override fun onBackStackChanged() {
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent!!.action == "android.hardware.usb.action.USB_DEVICE_ATTACHED") {
            val terminal =
                supportFragmentManager.findFragmentByTag("terminal") as TerminalFragment?
            terminal?.status("USB device detected")
        }

    }
}