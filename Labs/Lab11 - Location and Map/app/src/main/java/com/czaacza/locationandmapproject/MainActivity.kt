package com.czaacza.locationandmapproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val locationApi: LocationApi = LocationApi(this)

            locationApi.startLocationUpdates()
        }
    }
}
