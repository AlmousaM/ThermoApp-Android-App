package com.example.simpleusbserialreveng

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener, DeviceFragment.onTerminalFragmentStarted, TerminalFragment.onTerminalDisconnected {

    private var deviceFragment: DeviceFragment = DeviceFragment()
    private var terminalFragment: TerminalFragment = TerminalFragment()
    private var cameraFragment: CameraFragment = CameraFragment()
    private var settingFragment: SettingFragment = SettingFragment()
    private var activeFragment: Fragment = deviceFragment
    private lateinit var buttomNavView: BottomNavigationView



    //fragment support manager
    private val fragmentTransaction = supportFragmentManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.addOnBackStackChangedListener(this)
        //if (savedInstanceState == null) supportFragmentManager.beginTransaction()
          //  .add(R.id.fragment, DeviceFragment(), "devices").commit()
        //else onBackStackChanged()
        buttomNavView = findViewById(R.id.bottomNavView)



        fragmentTransaction.beginTransaction().apply {
            add(R.id.fragmentContainer, deviceFragment, "deviceFragment")
            add(R.id.fragmentContainer, cameraFragment, "pictureFragment").hide(cameraFragment)
            add(R.id.fragmentContainer, settingFragment, "settingFragment").hide(settingFragment)
        }.commit()

        //default selected item
        buttomNavView.selectedItemId = R.id.action_measure


        ButtonNavigationListener()

    }

    override fun onBackStackChanged() {
    }


    private fun ButtonNavigationListener() {
        buttomNavView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_measure -> {

                    if(fragmentTransaction.findFragmentByTag("terminal") as TerminalFragment?  == terminalFragment )
                    {
                        terminalFragment?.let {
                            fragmentTransaction.beginTransaction().hide(activeFragment).show(it).commit()
                        }
                        activeFragment = terminalFragment
                        Toast.makeText(this,"if terminalfragment statment true", Toast.LENGTH_SHORT).show()
                        true

                    }
                    else
                    {
                        Toast.makeText(this,"if terminalfragment statment false", Toast.LENGTH_SHORT).show()
                        fragmentTransaction.beginTransaction().hide(activeFragment).show(deviceFragment).commit()
                        activeFragment = deviceFragment
                        true
                    }

                }


                R.id.action_take_picture -> {
                    fragmentTransaction.beginTransaction().hide(activeFragment).show(cameraFragment).commit()
                    activeFragment = cameraFragment
                    Toast.makeText(this,"picture menu item selected", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.action_settings -> {
                    fragmentTransaction.beginTransaction().hide(activeFragment).show(settingFragment).commit()
                    activeFragment = settingFragment
                    Toast.makeText(this,"Setting menu item selected", Toast.LENGTH_SHORT).show()
                    true

                }
                else -> false
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent!!.action == "android.hardware.usb.action.USB_DEVICE_ATTACHED") {
            val terminal =
                supportFragmentManager.findFragmentByTag("terminal") as TerminalFragment?
            terminal?.status("USB device detected")
        }

    }

    override fun addTerminalFragmentToMenu(fragment: Fragment) {
        fragmentTransaction.beginTransaction().add(R.id.fragmentContainer, fragment, "terminal").hide(activeFragment).commit()
        activeFragment = fragment
        terminalFragment = fragment as TerminalFragment
        Toast.makeText(this,"Mainactivity callback happened", Toast.LENGTH_SHORT).show()
    }

    override fun showDeviceFragment() {
        //remove old terminal fragemnt
        fragmentTransaction.beginTransaction().detach(terminalFragment).commit()
        //remove old device fragemnt
        fragmentTransaction.beginTransaction().detach(deviceFragment).commit()

        //create a bundle to add unique args to differentiate between connected and unconnected terminal
        val args = Bundle()
        args.putInt("connected", 0)
        //get new terminal fragment with connected status
        terminalFragment = TerminalFragment()
        terminalFragment.arguments = args
        //get new device fragment
        deviceFragment = DeviceFragment()
        //show new device fragment
        fragmentTransaction.beginTransaction().add(R.id.fragmentContainer, deviceFragment, "device").hide(activeFragment).commit()
        //set active fragment as device fragment
        activeFragment = deviceFragment;
    }


}