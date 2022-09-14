package com.example.localisationmap

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.example.localisationmap.ui.theme.LocalisationMapTheme
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        if ((Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))
        { ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0) }

        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        setContent {
            val locationApi: LocationApi = LocationApi(this)
            val mapViewModel: MapViewModel = MapViewModel(
                app = application,
                map = getMap(context = this)
            )
            locationApi.requestLastLocation()
            LocalisationMapTheme {
                ShowItems(mapViewModel, locationApi)
            }
        }
    }
}
@Composable
fun getMap(context: Context): MapView {
    val map = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }
    return map
}

@Composable
fun ShowMap(
    mapViewModel: MapViewModel,
    locationApi: LocationApi,
) {
    val currentLatitude = locationApi.latitude.observeAsState()
    val currentLongitude = locationApi.longitude.observeAsState()
    val startGeopoint = GeoPoint(currentLatitude.value!!, currentLongitude.value!!)

    val map = mapViewModel.map
    mapViewModel.initializeMap()
    val marker = Marker(map)

    if (!mapViewModel.localisationready && currentLatitude.value != 0.0 && currentLongitude.value != 0.0) {
        mapViewModel.initializePosition(startGeopoint)
        mapViewModel.setMarker(marker, startGeopoint)
    }

    AndroidView({ map })
}

@Composable
fun ShowItems(mapViewModel: MapViewModel, locationApi: LocationApi, ) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val currentCoordinates = locationApi.requestLastLocation()
                    mapViewModel.setMapCenter(
                        currentCoordinates
                    )
                    mapViewModel.setMarker(
                        Marker(mapViewModel.map),
                        currentCoordinates
                    )
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(Icons.Default.LocationOn, "Location")
            }
        })
    {
        Box(
            modifier = Modifier.fillMaxHeight(),
        ) {
            ShowMap(mapViewModel, locationApi)
        }
    }
}