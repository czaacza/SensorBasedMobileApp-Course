package com.czaacza.bluetoothbeaconproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.czaacza.bluetoothbeaconproject.ui.theme.BluetoothBeaconProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BluetoothBeaconProjectTheme {

            }
        }
    }
}