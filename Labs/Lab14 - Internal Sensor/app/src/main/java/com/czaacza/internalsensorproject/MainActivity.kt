package com.czaacza.internalsensorproject

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.czaacza.internalsensorproject.ui.theme.InternalSensorProjectTheme
import java.lang.Math.floor
import kotlin.math.floor

class MainActivity : ComponentActivity() {
    lateinit var sensorManager: SensorManager
    lateinit var sensorViewModel: SensorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorViewModel = SensorViewModel(sensorManager)

        Log.d("DBG", "${sensorManager.getSensorList(Sensor.TYPE_ALL)}")

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null ||
            sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) == null ||
            sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null
        ) {
            Log.d("DBG", "Error, Sensor does not exist.")
            return
        }

        super.onCreate(savedInstanceState)
        setContent {
            InternalSensorProjectTheme {
                ShowContent(sensorViewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            sensorViewModel.sensorEventListener,
            sensorViewModel.accelerometer,
            SensorManager.SENSOR_DELAY_UI
        )

        sensorManager.registerListener(
            sensorViewModel.sensorEventListener,
            sensorViewModel.gravity,
            SensorManager.SENSOR_DELAY_UI
        )
        sensorManager.registerListener(
            sensorViewModel.sensorEventListener,
            sensorViewModel.gyroscope,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorViewModel.sensorEventListener)
    }
}

@Composable
fun ShowContent(sensorViewModel: SensorViewModel) {
    val accelerometerData = sensorViewModel.accelerometerData.observeAsState()
    val gravityData = sensorViewModel.gravityData.observeAsState()
    val gyroscopeData = sensorViewModel.gyroscopeData.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Physics Checker", fontSize = 36.sp, fontWeight = FontWeight(700))
            Text(
                text = "What's going on with my phone?",
                fontSize = 16.sp,
                fontWeight = FontWeight(600)
            )
        }
        Column(modifier = Modifier.padding(20.dp)) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp), horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Acceleration forces", fontSize = 20.sp, fontWeight = FontWeight(600))
                if (accelerometerData.value != null) {
                    Text(text = "x: ${accelerometerData.value!![0]}")
                    Text(text = "y: ${accelerometerData.value!![1]}")
                    Text(text = "z: ${accelerometerData.value!![2]}")
                } else {
                    Text(text = "None")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp), horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Gravity forces", fontSize = 20.sp, fontWeight = FontWeight(600))
                if (gravityData.value != null) {
                    Text(text = "x: ${gravityData.value!![0]}")
                    Text(text = "y: ${gravityData.value!![1]}")
                    Text(text = "z: ${gravityData.value!![2]}")
                } else {
                    Text(text = "None")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp), horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Rotation", fontSize = 20.sp, fontWeight = FontWeight(600))
                if (gyroscopeData.value != null) {
                    Text(text = "x: ${floor(gyroscopeData.value!![0])}°")
                    Text(text = "y: ${floor(gyroscopeData.value!![1])}°")
                    Text(text = "z: ${floor(gyroscopeData.value!![2])}°")
                } else {
                    Text(text = "None")
                }

            }

        }
    }
}
