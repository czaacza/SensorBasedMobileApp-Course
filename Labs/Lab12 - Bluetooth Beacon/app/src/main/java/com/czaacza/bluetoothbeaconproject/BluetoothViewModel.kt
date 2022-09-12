package com.czaacza.bluetoothbeaconproject

import android.Manifest
import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.lifecycle.AndroidViewModel

class BluetoothViewModel(application: Application, val activity: Activity ,bluetoothManager: BluetoothManager) :
    AndroidViewModel(application) {
    var bluetoothAdapter: BluetoothAdapter? = null
    val app = application


    init {
        bluetoothAdapter = bluetoothManager.adapter
    }


    fun checkPermissions(): Boolean {
        if (bluetoothAdapter == null || !bluetoothAdapter!!.isEnabled) {
            Log.d("DBG", "No Bluetooth LE capability")
            return false
        } else if (ContextCompat.checkSelfPermission(
                app,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("DBG", "No fine location access")
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            ); return true
        }
        return true
    }
}