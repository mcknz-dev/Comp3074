
package ca.gbc.comp3071.hodgson_mackenzie.lab5

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var locationManager: LocationManager
    private lateinit var latitudeText: TextView
    private lateinit var longitudeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        latitudeText = findViewById(R.id.latitudeText)
        longitudeText = findViewById(R.id.longitudeText)
        locationManager =
            getSystemService(LOCATION_SERVICE) as LocationManager

        if (hasLocationPermission()) {
            getLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getLocation() {
        if (!hasLocationPermission()) return

        try {
            val provider = when {
                locationManager.isProviderEnabled(
                    LocationManager.GPS_PROVIDER
                ) -> LocationManager.GPS_PROVIDER

                locationManager.isProviderEnabled(
                    LocationManager.NETWORK_PROVIDER
                ) -> LocationManager.NETWORK_PROVIDER

                else -> null
            }

            if (provider == null) {
                Toast.makeText(
                    this,
                    "Please enable location services",
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            val lastLocation =
                locationManager.getLastKnownLocation(provider)

            if (lastLocation != null) {
                onLocationChanged(lastLocation)
            } else {
                latitudeText.text = "Latitude: Getting location..."
                longitudeText.text = "Longitude: Getting location..."
            }

            locationManager.requestLocationUpdates(
                provider,
                2000L,
                1f,
                this
            )
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Unable to retrieve location",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onLocationChanged(location: Location) {
        latitudeText.text = "Latitude: ${location.latitude}"
        longitudeText.text = "Longitude: ${location.longitude}"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )

        if (requestCode == 100 && hasLocationPermission()) {
            getLocation()
        } else if (requestCode == 100) {
            latitudeText.text = "Latitude: Permission denied"
            longitudeText.text = "Longitude: Permission denied"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.removeUpdates(this)
    }
}
