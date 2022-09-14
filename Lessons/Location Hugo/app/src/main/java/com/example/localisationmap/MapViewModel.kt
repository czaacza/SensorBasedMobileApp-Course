package com.example.localisationmap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapViewModel(app:Application,var map: MapView):AndroidViewModel(app) {
    var localisationready = false


    fun initializeMap() {
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.controller.setZoom(9.0)
        map.setMultiTouchControls(true)
    }

    fun initializePosition(coordinate: GeoPoint) {
        if (!localisationready) {
            setMapCenter(coordinate)
            localisationready = true;
        }
    }
    fun setMapCenter(coordinate: GeoPoint) {
        map.controller.setCenter(coordinate)
    }
    fun setMarker(
        marker: Marker,
        coordinate: GeoPoint,
        title: String = "Your are here",
        description: String = ""
    ) {
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_LEFT)
        marker.position = coordinate
        marker.closeInfoWindow()
        marker.title = title
        marker.subDescription = description + "\nlatitude: ${coordinate.latitude}, longitude: ${coordinate.longitude}"
        map.overlays.add(marker)
        map.invalidate()
    }
}