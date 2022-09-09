package com.czaacza.locationandmapproject

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY

class LocationApi(val context: Activity) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationCallback = getLocationCallback()


    var latitude : Double = 0.0
    var longitude: Double = 0.0


    fun requestLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                Log.d("DBG", "last location latitude: ${it.latitude}, longitude: ${it.longitude}")
            }
        }
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
        }
    }

    fun stopLocationUpdates(){
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun getLocationCallback(): LocationCallback {
        return object: LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if(locationResult.lastLocation == null){
                    return
                }
                latitude = locationResult.lastLocation!!.latitude
                longitude = locationResult.lastLocation!!.longitude

                Log.d("DBG", "last location update: latitude: $latitude, longitude: $longitude")
            }
        }
    }

    private fun getLocationRequest() : LocationRequest{
        return LocationRequest
            .create()
            .setInterval(2000)
            .setPriority(PRIORITY_HIGH_ACCURACY)
    }


}