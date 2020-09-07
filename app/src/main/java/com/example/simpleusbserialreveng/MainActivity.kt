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


class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener, DeviceFragment.onTerminalFragmentStarted, TerminalFragment.OnTerminalDisconect {

    private val deviceFragment: DeviceFragment = DeviceFragment()
    private var terminalFragment: TerminalFragment = TerminalFragment()
    private val pictureFragment: PictuteFragment = PictuteFragment()
    internal var activeFragment: Fragment = deviceFragment
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
            add(R.id.fragmentContainer, deviceFragment, "deviceFragment").hide(deviceFragment)
            add(R.id.fragmentContainer, pictureFragment, "pictureFragment").hide(pictureFragment)
        }.commit()

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
                    fragmentTransaction.beginTransaction().hide(activeFragment).show(pictureFragment).commit()
                    activeFragment = pictureFragment
                    Toast.makeText(this,"picture menu item selected", Toast.LENGTH_SHORT).show()
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

    override fun RemoveTerminalFragment() {



    }


}