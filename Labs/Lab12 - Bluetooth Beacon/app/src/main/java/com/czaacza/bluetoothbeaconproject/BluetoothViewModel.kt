package com.czaacza.bluetoothbeaconproject

import android.Manifest
import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class BluetoothViewModel(
    application: Application,
    val activity: Activity,
    bluetoothManager: BluetoothManager
) :
    AndroidViewModel(application) {
    var bluetoothAdapter: BluetoothAdapter? = null
    private val app = application

    private val results = HashMap<String, ScanResult>()
    val scanResultsLiveData = MutableLiveData<List<ScanResult>>(null)

    var isScanning = MutableLiveData<Boolean>(false)

    private var scanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            val foundDeviceAddress = result.device.address

            results[foundDeviceAddress] = result
            Log.d("DBG", "Device address: $foundDeviceAddress (${result.isConnectable})")
        }
    }

    init {
        bluetoothAdapter = bluetoothManager.adapter
    }


    @RequiresApi(Build.VERSION_CODES.S)
    fun checkPermissions(): Boolean {
        if (bluetoothAdapter == null || !bluetoothAdapter!!.isEnabled) {
            Log.d("DBG", "No Bluetooth LE capability")
            return false
        } else if (
            ActivityCompat.checkSelfPermission(app, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(
                "DBG",
                "No fine location access"
            )
            requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            ); return true // assuming that the user grants permission
        }
        return true
    }

    fun checkScanPermissions() {
        if (ContextCompat.checkSelfPermission(
                app,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("DBG", "No bluetooth scan access")
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.BLUETOOTH_SCAN),
                1
            )
        }
    }

    fun scanDevices() {
        val scanner = bluetoothAdapter!!.bluetoothLeScanner

        viewModelScope.launch(Dispatchers.IO) {
            val scanSettings = ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setReportDelay(0)
                .build()

            isScanning.postValue(true)

            if (Build.VERSION.SDK_INT >= 31 && ActivityCompat.checkSelfPermission(
                    app,
                    Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("DBG", "No permissions")
                return@launch
            }

            scanner.startScan(null, scanSettings, scanCallback)
            delay(SCAN_PERIOD)

            scanner.stopScan(scanCallback)

            isScanning.postValue(false)
            scanResultsLiveData.postValue(results.values.toList())
        }
    }

    companion object GattAttributes {
        const val SCAN_PERIOD: Long = 5000
        const val STATE_DISCONNECTED = 0
        const val STATE_CONNECTING = 1
        const val STATE_CONNECTED = 2
        val UUID_HEART_RATE_MEASUREMENT = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb")
        val UUID_HEART_RATE_SERVICE = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb")
        val UUID_CLIENT_CHARACTERISTIC_CONFIG =
            UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
    }

}