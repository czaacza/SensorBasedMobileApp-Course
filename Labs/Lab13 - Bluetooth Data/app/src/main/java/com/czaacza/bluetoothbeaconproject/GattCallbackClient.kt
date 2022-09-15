package com.czaacza.bluetoothbeaconproject

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import android.util.Log
import java.nio.charset.StandardCharsets
import java.util.*


class GattCallbackClient(val context: Context) : BluetoothGattCallback() {
    @SuppressLint("MissingPermission")
    override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)

        if (status == BluetoothGatt.GATT_FAILURE) {
            Log.d("DBG", "GATT connection failure")
        } else if (status == BluetoothGatt.GATT_SUCCESS) {
            Log.d("DBG", "GATT connection success, newState: ${newState}, ")
        }
        if (newState == BluetoothProfile.STATE_CONNECTED) {
//            if (ActivityCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.BLUETOOTH_CONNECT
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                return
//            }
            Log.d("DBG", "Connected GATT service")
            gatt.discoverServices();
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
        }

    }

    @SuppressLint("MissingPermission")
    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        super.onServicesDiscovered(gatt, status)
        if (status != BluetoothGatt.GATT_SUCCESS) {
            return
        }
        Log.d("DBG", "onServicesDiscovered()")


        for (gattService in gatt.services) {
            Log.d("DBG", "Service ${gattService.uuid}")
            Log.d("DBG", "HEART_RATE_SERVICE_UUID: $HEART_RATE_SERVICE_UUID")
            if (gattService.uuid == HEART_RATE_SERVICE_UUID) {
                for (gattCharacteristic in gattService.characteristics) {
                    Log.d("DBG", gattCharacteristic.uuid.toString())
                }

                val characteristic = gatt.getService(HEART_RATE_SERVICE_UUID).getCharacteristic(
                    HEART_RATE_MEASUREMENT_CHAR_UUID
                )
                gatt.readCharacteristic(characteristic)

                if (gatt.setCharacteristicNotification(characteristic, true)) {
                    val descriptor = characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_UUID)
                    descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                    val writing = gatt.writeDescriptor(descriptor)
                }

                gatt.setCharacteristicNotification(characteristic, true)
            }
        }
    }

    override fun onDescriptorWrite(
        gatt: BluetoothGatt,
        descriptor: BluetoothGattDescriptor,
        status: Int
    ) {
        Log.d("DBG", "onDescriptorWrite")
    }

    override fun onCharacteristicChanged(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic
    ) {
        Log.d("DBG", "Characteristic data received, value: ${characteristic.value}")
    }

    override fun onCharacteristicRead(
        gatt: BluetoothGatt?,
        characteristic: BluetoothGattCharacteristic?,
        status: Int
    ) {
        if (characteristic != null) {
            Log.d("DBG", "Characteristic data READ, value: ${characteristic.value}")
            val str = String(characteristic.value, StandardCharsets.UTF_8)
            Log.d("DBG","characteristic value: $str")
        }
    }

    companion object {
        val HEART_RATE_SERVICE_UUID = convertFromInteger(0x1801)
        val HEART_RATE_MEASUREMENT_CHAR_UUID = convertFromInteger(0x2a01)
        val CLIENT_CHARACTERISTIC_CONFIG_UUID = convertFromInteger(0x2902)


        private fun convertFromInteger(i: Int): UUID {
            val MSB = 0x0000000000001000L
            val LSB = -0x7fffff7fa064cb05L
            val value = (i and -0x1).toLong()
            return UUID(MSB or (value shl 32), LSB)
        }
    }
}