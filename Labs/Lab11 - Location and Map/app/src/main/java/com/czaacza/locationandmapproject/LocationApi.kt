package com.czaacza.locationandmapproject

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import org.osmdroid.util.GeoPoint
import java.io.IOException
import java.util.*

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
                if (locationsList.size == 1) {
                    startTrackLocation.value = locationResult.lastLocation
                }
                endTrackLocation.value = locationsList[locationsList.size - 1]
            }
        }
    }

    private fun getLocationRequest(): LocationRequest {
        return LocationRequest
            .create()
            .setInterval(INTERVAL_TIME)
            .setPriority(PRIORITY_HIGH_ACCURACY)
    }

    fun getTotalDistance(): Float {
        if (endTrackLocation.value != null && startTrackLocation.value != null) {
            return endTrackLocation.value!!.distanceTo(startTrackLocation.value)
        }
        return 0.0f
    }

    fun getAddress(context: Context, geoPoint: GeoPoint): String {
        val geocoder = Geocoder(context)
        var currentAddress = ""
        if (Build.VERSION.SDK_INT >= 33) {
            currentAddress =
                geocoder.getFromLocation(geoPoint.latitude, geoPoint.longitude, 1)?.first()
                    ?.getAddressLine(0) ?: ""
        }
        return currentAddress
    }
}