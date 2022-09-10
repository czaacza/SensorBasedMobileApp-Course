package com.czaacza.locationandmapproject

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import org.osmdroid.util.GeoPoint

const val INTERVAL_TIME = 1000L

class LocationApi(val context: Activity) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationCallback = getLocationCallback()

    var latitude = MutableLiveData(0.0)
    var longitude = MutableLiveData(0.0)
    var isLocationTracked = MutableLiveData(false)
    private var locationsList = mutableListOf<Location>()
    var trackedLocations = MutableLiveData<MutableList<Location>>(locationsList)
    var startTrackLocation = MutableLiveData<Location>()
    var endTrackLocation = MutableLiveData<Location>()

    fun requestLastLocation(): GeoPoint {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                latitude.value = it.latitude
                longitude.value = it.longitude

                Log.d(
                    "DBG",
                    "last location latitude: ${latitude.value}, longitude: ${longitude.value}"
                )
            }
        }
        return GeoPoint(latitude.value!!, longitude.value!!)
    }

    fun startLocationUpdates() {
        val locationRequest = getLocationRequest()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            isLocationTracked.value = true;
        }
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        isLocationTracked.value = false
    }

    private fun getLocationCallback(): LocationCallback {
        return object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.lastLocation == null) {
                    return
                }
                latitude.value = locationResult.lastLocation!!.latitude
                longitude.value = locationResult.lastLocation!!.longitude
                locationsList.add(locationResult.lastLocation!!)
//                Log.d("DBG", trackedLocations.value.toString())
//                Log.d("DBG", "last location update: latitude: ${latitude.value}, longitude: ${longitude.value}")
            }
        }
    }

    private fun getLocationRequest(): LocationRequest {
        return LocationRequest
            .create()
            .setInterval(INTERVAL_TIME)
            .setPriority(PRIORITY_HIGH_ACCURACY)
    }
}