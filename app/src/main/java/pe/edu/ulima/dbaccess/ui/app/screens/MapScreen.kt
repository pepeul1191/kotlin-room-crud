package pe.edu.ulima.dbaccess.ui.app.screens

import android.content.Context
import android.preference.PreferenceManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.MapController
import org.osmdroid.views.overlay.Marker

@Composable
fun MapViewContainer(context: Context) {
    // Initialize the map configuration
    Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))

    // Create a MapView
    val mapView = rememberMapView(context)

    // Set the tile source (e.g., OpenStreetMap)
    mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)

    // Set the map controller
    val mapController = MapController(mapView)
    mapController.setZoom(15.0)
    // Set the map center
    val mapCenter = GeoPoint(-12.083870, -77.015840)
    mapController.setCenter(mapCenter)
    val marker = Marker(mapView)
    marker.position = mapCenter
    mapView.overlays.add(marker)
    marker.setOnMarkerClickListener { marker, mapView ->
        // Handle marker click event
        val markerPosition = marker.position
        println("+++++++++++++++++++++++++++++++++")
        true
    }

    // Compose the MapView into the UI
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { mapView }
    )
}

@Composable
private fun rememberMapView(context: Context): MapView {
    val mapView = remember {
        MapView(context)
    }

    return mapView
}