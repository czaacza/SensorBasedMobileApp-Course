package com.czaacza.bluetoothbeaconproject

import android.Manifest
import android.bluetooth.*
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat

class GattCallbackClient(val context: Context) : BluetoothGattCallback() {
    override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)

        if (status == BluetoothGatt.GATT_FAILURE) {
            Log.d("DBG", "GATT connection failure")
            return
        } else if (status == BluetoothGatt.GATT_SUCCESS) {
            Log.d("DBG", "GATT connection success")
            return
        }
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            Log.d("DBG", "Connected GATT service")
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            gatt.discoverServices();
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
        }

    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        super.onServicesDiscovered(gatt, status)
        if (status != BluetoothGatt.GATT_SUCCESS) {
            return
        }
        Log.d("DBG", "onServicesDiscovered()")
        for (gattService in gatt.services) {
        }
    }

    override fun onDescriptorWrite(gatt: BluetoothGatt, descriptor: BluetoothGattDescriptor, status: Int) { Log.d("DBG", "onDescriptorWrite")
    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) { Log.d("DBG", "Characteristic data received")
    }
}