package pe.edu.ulima.dbaccess.configs

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class GpsTracker(private val context: Context) {
    var latitude by mutableStateOf(0.0)
        private set
    var longitude by mutableStateOf(0.0)
        private set

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d("GPS_TRACKER", "cambio")
            latitude = location.latitude
            longitude = location.longitude
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    fun startTracking() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_BETWEEN_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES,
                locationListener
            )
        }
    }

    fun stopTracking() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.removeUpdates(locationListener)
    }

    companion object {
        private const val MIN_TIME_BETWEEN_UPDATES: Long = 1000 // 1 second
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Float = 1f // 1 meter
    }
}

@Composable
fun GpsTrackerView(context: Context) {
    val gpsTracker = remember { GpsTracker(context) }
    Log.d("GPS_TRACKER", "GpsTrackerView")
    DisposableEffect(Unit) {
        gpsTracker.startTracking()
        onDispose {
            gpsTracker.stopTracking()
        }
    }

    androidx.compose.runtime.SideEffect {
        gpsTracker.startTracking()
    }

    Column {
        Text("Latitude: ${gpsTracker.latitude}")
        Text("Longitude: ${gpsTracker.longitude}")
    }
}