package com.example.simpleusbserialreveng

import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber


class DeviceFragment: Fragment() {
    private lateinit var refreshButton: Button
    private lateinit var connectButton: Button
    private lateinit var deviceName: TextView
    private lateinit var productId: TextView
    private lateinit var vendorId: TextView

    private var device: UsbDevice? = null
    private var port: UsbSerialPort? = null
    private var driver: UsbSerialDriver? = null
    private val baudRate = 9600

    //getting instance of activeFragment from main activity
    private var activeFragment = MainActivity().activeFragment




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_device, container,false)
        refreshButton = view!!.findViewById(R.id.refreshButton)
        connectButton = view.findViewById(R.id.connectButton)
        deviceName = view.findViewById(R.id.deviceName)
        productId = view.findViewById(R.id.productId)
        vendorId = view.findViewById(R.id.vendorID)
        return view
    }

    override fun onStart() {
        super.onStart()
        refreshButton.setOnClickListener { refresh()
            if (driver != null) connectButton.isEnabled = true
        }
        connectButton.setOnClickListener {
            if (driver != null)
            {
                val args = Bundle()
                args.putInt("device", driver!!.device.deviceId)
                args.putInt("port", 0)
                args.putInt("baud", baudRate)
                val fragment: Fragment = TerminalFragment()
                fragment.arguments = args
                fragmentManager!!.beginTransaction().replace(R.id.fragmentContainer, fragment, "terminal")
                    .addToBackStack(null).commit()
                activeFragment = fragment
            }
        }
    }


    private fun refresh() {
        val usbManager = activity!!.getSystemService(Context.USB_SERVICE) as UsbManager
        val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager)
        if (availableDrivers.isEmpty()) {
            deviceName.text = "Not device found"
            productId.text = "NA"
            vendorId.text = "NA"
            device = null
            port= null
            driver = null
            return;
        }

        //get the first driver
        driver = availableDrivers[0]
        //get the first port
        port = driver!!.ports[0]
        deviceName.text = driver!!.javaClass.simpleName
        productId.text = driver!!.device.deviceId.toString()
        vendorId.text = driver!!.device.vendorId.toString()
    }
}