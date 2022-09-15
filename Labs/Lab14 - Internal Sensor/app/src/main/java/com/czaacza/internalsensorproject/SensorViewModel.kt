package com.czaacza.internalsensorproject

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlin.math.abs

class SensorViewModel(val sensorManager: SensorManager) {

    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    var lastAccelerometerValues: List<Float> = mutableListOf(0.0f, 0.0f, 0.0f)
    val accelerometerData = MutableLiveData<List<Float>>()

    val gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
    var lastGravityValues: List<Float> = mutableListOf(0.0f, 0.0f, 0.0f)
    val gravityData = MutableLiveData<List<Float>>()

    val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    var lastGyroscopeValues: List<Float> = mutableListOf(0.0f, 0.0f, 0.0f)
    val gyroscopeData = MutableLiveData<List<Float>>()


    val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(sensorEvent: SensorEvent?) {
            if (sensorEvent == null) {
                return
            }
            if (sensorEvent.sensor == accelerometer) {
                val x = sensorEvent.values[0]
                val y = sensorEvent.values[1]
                val z = sensorEvent.values[2]
                val limit = 0.4

                if (abs(x - lastAccelerometerValues[0]) > limit || abs(y - lastAccelerometerValues[1]) > limit || abs(
                        z - lastAccelerometerValues[2]
                    ) > limit
                ) {
                    accelerometerData.postValue(sensorEvent.values.toList())

                    lastAccelerometerValues = listOf(x, y, z)
                }
            }

            if (sensorEvent.sensor == gravity) {
                val x = sensorEvent.values[0]
                val y = sensorEvent.values[1]
                val z = sensorEvent.values[2]
                val limit = 0.1
                if (abs(x - lastGravityValues[0]) > limit || abs(y - lastGravityValues[1]) > limit || abs(z - lastGravityValues[2]) > limit
                ) {
                    gravityData.postValue(sensorEvent.values.toList())

                    lastGravityValues = listOf(x, y, z)
                }
            }

            if (sensorEvent.sensor == gyroscope) {
                val x = sensorEvent.values[0]
                val y = sensorEvent.values[1]
                val z = sensorEvent.values[2]
                val limit = 0.05


                if (abs(x - lastGyroscopeValues[0]) > limit || abs(y - lastGyroscopeValues[1]) > limit || abs(
                        z - lastGyroscopeValues[2]
                    ) > limit
                ) {
                    val valuesInDegrees =
                        mutableListOf<Float>(x * 57.2957795f, y * 57.2957795f, z * 57.2957795f)
                    gyroscopeData.postValue(valuesInDegrees)

                    lastGyroscopeValues = listOf(x, y, z)
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {
            Log.d("DBG", "Accuracy changed")
        }

    }
}

