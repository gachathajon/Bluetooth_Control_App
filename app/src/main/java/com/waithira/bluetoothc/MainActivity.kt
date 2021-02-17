package com.waithira.bluetoothc

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*


//bluetooth adapter

lateinit var bAdapter:BluetoothAdapter

class MainActivity : AppCompatActivity() {
    
    private val REQUEST_CODE_ENABLE_BT:Int = 1;
    private val REQUEST_CODE_DISCOVERABLE_BT:Int = 2;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //init bluetooth adapter
        bAdapter = BluetoothAdapter.getDefaultAdapter()
        //check if bluetooth is on/off
        if (bAdapter==null){
           bluetoothStatusTv.text = "Bluetooth not available"
        }
        else {
            bluetoothStatusTv.text = "Bluetooth is available"
        }
        //set the image according to bluetoothStatus
        if (bAdapter.isEnabled){
            //bluetooth is on
            bluetoothStatusIv.setImageResource(R.drawable.ic_bluetooth_on)
        }
        else if (!bAdapter.isEnabled){
            // bluuetoth is off
            bluetoothStatusIv.setImageResource(R.drawable.ic_bluetooth_off)
        }
        //turn on bluetooth
        bluetoothOnBtn.setOnClickListener { 
            if (bAdapter.isEnabled){
                //already enabled
                Toast.makeText(this, "Already on", Toast.LENGTH_LONG).show()
            }
            else{//turn on bluetooth
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
            }
        }
        //turn off bluetooth
        bluetoothOffBtn.setOnClickListener {
            if (!bAdapter.isEnabled){
                //already enabled
                Toast.makeText(this, "Already off", Toast.LENGTH_LONG).show()
            }
            else{//turn on bluetooth
                bAdapter.disable()
                bluetoothStatusIv.setImageResource(R.drawable.ic_bluetooth_off)
                Toast.makeText(this, "Bluetooth turned off", Toast.LENGTH_LONG).show()

            }
        }
        //discoverable bluetooth
        discoverableBtn.setOnClickListener {
            if (!bAdapter.isDiscovering){
                Toast.makeText(this, "Making you device discoverable", Toast.LENGTH_LONG).show()
                val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent, REQUEST_CODE_DISCOVERABLE_BT)

            }
        }
        //get list of paired devices
        pairedBtn.setOnClickListener {
            if (bAdapter.isEnabled){
                pairedTv.text = "Paired Devices"
                //get list of paired devices
                val devices = bAdapter.bondedDevices
                for (device in devices){
                    val deviceName = device.name
                    val deviceAddress = device
                    pairedTv.append("\nDevice: $deviceName, $device")
                }
            }
            else{
                Toast.makeText(this, "Turn on bluetooth", Toast.LENGTH_LONG).show()

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            REQUEST_CODE_ENABLE_BT ->
                if (requestCode == Activity.RESULT_OK){
                    bluetoothStatusIv.setImageResource(R.drawable.ic_bluetooth_on)
                    Toast.makeText(this, "Bluetooth is on", Toast.LENGTH_LONG).show()

                }
              else if (requestCode == Activity.RESULT_CANCELED){
                    Toast.makeText(this, "Could not connect", Toast.LENGTH_LONG).show()
              }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}