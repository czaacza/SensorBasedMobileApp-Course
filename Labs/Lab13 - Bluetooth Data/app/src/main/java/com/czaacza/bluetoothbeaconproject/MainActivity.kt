package com.czaacza.bluetoothbeaconproject

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.czaacza.bluetoothbeaconproject.ui.theme.BluetoothBeaconProjectTheme

class MainActivity : ComponentActivity() {

    private var mBluetoothAdapter: BluetoothAdapter? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("DBG", "No bluetooth scan access")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH_SCAN),
                1
            )
        }

        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothViewModel =
            BluetoothViewModel(application = application, this, bluetoothManager)

        if (!bluetoothViewModel.checkPermissions()) {
            return
        }

        setContent {
            BluetoothBeaconProjectTheme {
                ShowContent(bluetoothViewModel, this)
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun ShowContent(bluetoothViewModel: BluetoothViewModel, context : Context) {
    val scanResults = bluetoothViewModel.scanResultsLiveData.observeAsState()
    val isScanning = bluetoothViewModel.isScanning.observeAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(40.dp)
    ) {
        Button(
            onClick = {
                bluetoothViewModel.scanDevices()
            },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
        ) {
            val prefix: String = if (isScanning.value == true) {
                "Stop"
            } else {
                "Start"
            }
            Text(
                text = "$prefix scanning",
                fontSize = 22.sp,
                color = MaterialTheme.colors.onPrimary
            )
        }
        Spacer(modifier = Modifier.size(60.dp))

        Text(
            text = "Bluetooth device list:",
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            color = MaterialTheme.colors.onPrimary
        )

        if (isScanning.value == true) {
            CircularProgressIndicator(modifier = Modifier.padding(20.dp))
        }

        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize()
        ) {
            if (scanResults.value != null && isScanning.value != true) {
                items(scanResults.value!!.toList()) { result ->

                    val fontColor = if (result.isConnectable) {
                        MaterialTheme.colors.onPrimary
                    } else {
                        Color.Gray
                    }

                    val deviceName = if (result.device.name != null) {
                        result.device.name
                    } else {
                        ""
                    }

                    Text(
                        "${result.device.address} $deviceName ${result.rssi}dBm",
                        color = fontColor,
                        fontSize = 18.sp,
                        modifier = Modifier.selectable(selected = true, onClick = {
                            val device = result.device
                            val gattCallbackClient = GattCallbackClient(context)
                            val gattConnection = device.connectGatt(context, false, gattCallbackClient)
                        })
                    )
                }
            }
        }
    }
}
