package com.czaacza.bluetoothbeaconproject

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.czaacza.bluetoothbeaconproject.ui.theme.BluetoothBeaconProjectTheme

class MainActivity : ComponentActivity() {

    private var mBluetoothAdapter : BluetoothAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothViewModel = BluetoothViewModel(application = application,this ,bluetoothManager)
        if(!bluetoothViewModel.checkPermissions()){
            return
        }

        setContent {
            BluetoothBeaconProjectTheme {
                ShowContent()
            }
        }
    }
}

@Composable
fun ShowContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
    ) {
        Button(
            onClick = { /*TODO*/ },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
        ) {
            Text(text = "Start scanning", fontSize = 22.sp)
        }
        Spacer(modifier = Modifier.size(60.dp))

        Text(
            text = "Bluetooth device list:",
            fontSize = 20.sp,
            fontWeight = FontWeight(600)
        )

        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize()
        ) {
            item {
                Text(text = "sample item text", fontSize = 18.sp)
            }
        }
    }
}
