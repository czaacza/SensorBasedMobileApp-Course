package com.czaacza.locationandmapproject

import android.app.Application
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.io.IOException
import java.util.*


class MapViewModel(application: Application, val map: MapView) : AndroidViewModel(application) {

    var mapInitialized = false
    var positionInitialized = false

    fun initialize() {
        if (!mapInitialized) {
            map.setTileSource(TileSourceFactory.MAPNIK)
            map.setMultiTouchControls(true)
            map.controller.setZoom(9.0)
            mapInitialized = true
        }
    }

    fun initializePosition(coordinateGeoPoint: GeoPoint) {
        if (!positionInitialized) {
            setCenter(coordinateGeoPoint)
            positionInitialized = true;
        }
    }

    fun setCenter(coordinateGeoPoint: GeoPoint) {
        map.controller.setCenter(coordinateGeoPoint)
    }

    fun setMarker(
        marker: Marker,
        coordinateGeoPoint: GeoPoint,
        title: String = "Your position",
        description: String = ""
    ) {
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.position = coordinateGeoPoint
        marker.closeInfoWindow()
        marker.title = title
        marker.subDescription = description + "\nlatitude: ${coordinateGeoPoint.latitude}, longitude: ${coordinateGeoPoint.longitude}"
        map.overlays.add(marker)
        map.invalidate()
    }

    fun drawLine(locations: List<GeoPoint>) {
        val line = Polyline();
        if (locations.isNotEmpty()) {
            line.setPoints(locations)
        }
        map.overlays.add(line);
    }
}