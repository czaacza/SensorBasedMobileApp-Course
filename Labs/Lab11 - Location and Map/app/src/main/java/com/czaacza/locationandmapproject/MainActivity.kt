package com.czaacza.locationandmapproject

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.preference.PreferenceManager
import com.czaacza.locationandmapproject.ui.theme.LocationAndMapProjectTheme
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        setContent {
            val locationApi: LocationApi = LocationApi(this)
            val mapViewModel: MapViewModel = MapViewModel(
                application = application,
                map = getMap(context = this)
            )

            locationApi.requestLastLocation()

            LocationAndMapProjectTheme() {
                ShowLayout(mapViewModel, locationApi)
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
    mapViewModel.initialize()
    val marker = Marker(map)

    if (!mapViewModel.positionInitialized && currentLatitude.value != 0.0 && currentLongitude.value != 0.0) {
        mapViewModel.initializePosition(startGeopoint)
        mapViewModel.setMarker(marker, startGeopoint)
    }

    AndroidView({ map })
}

@Composable
fun ShowLayout(
    mapViewModel: MapViewModel,
    locationApi: LocationApi,
) {
    val isTrackingButtonClicked = locationApi.isLocationTracked.observeAsState().value
    val coordinates = GeoPoint(
        locationApi.latitude.observeAsState().value!!,
        locationApi.longitude.observeAsState().value!!
    )
    val locations = locationApi.trackedLocations.observeAsState()
    val locationsAsGeopoints = mutableListOf<GeoPoint>()
    if (locations != null) {
        for (location in locations.value!!) {
            locationsAsGeopoints.add(GeoPoint(location.latitude, location.longitude))
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val currentCoordinates = locationApi.requestLastLocation()
                    mapViewModel.setCenter(
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
        }
    ) {
        Column() {
            Box(
                modifier = Modifier.fillMaxHeight(0.8f),
            ) {
                ShowMap(mapViewModel, locationApi)
                Column(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(modifier = Modifier.width(150.dp), onClick = {
                        if (isTrackingButtonClicked == false) {
                            locationApi.startLocationUpdates()
                            mapViewModel.setMarker(
                                Marker(mapViewModel.map),
                                coordinates,
                                "Track started"
                            )
                        } else {
                            locationApi.stopLocationUpdates()
                            mapViewModel.setMarker(
                                Marker(mapViewModel.map),
                                coordinates, "Track ended"
                            )
                        }
                    }) {
                        var prefix: String = ""
                        prefix = if (isTrackingButtonClicked == false) {
                            "Start"
                        } else {
                            "Stop"
                        }
                        Text(text = "$prefix tracking")
                    }
                }
            }
            Column() {
                Row(modifier = Modifier.padding(20.dp)) {
                    Text(text = "Total walked distance: ", fontSize = 20.sp)
                    Text(text = "", fontSize = 20.sp, fontWeight = FontWeight(700))
                }
            }
        }
    }


}