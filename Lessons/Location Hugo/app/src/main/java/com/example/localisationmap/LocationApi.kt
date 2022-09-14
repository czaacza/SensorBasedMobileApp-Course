package com.example.localisationmap

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import org.osmdroid.util.GeoPoint

class LocationApi(var context: Activity) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    var latitude = MutableLiveData(0.0)
    var longitude = MutableLiveData(0.0)

    fun requestLastLocation(): GeoPoint {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("fuck","here1")
            fusedLocationClient.lastLocation.addOnSuccessListener {
                latitude.value = it.latitude
                longitude.value = it.longitude
                Log.i("fuck","here2")
            }
        }
        Log.i("fuck", "${latitude.value}")
        return GeoPoint(latitude.value!!, longitude.value!!)
    }
}