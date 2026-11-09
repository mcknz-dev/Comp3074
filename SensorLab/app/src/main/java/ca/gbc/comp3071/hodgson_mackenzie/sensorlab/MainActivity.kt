
package ca.gbc.comp3071.hodgson_mackenzie.sensorlab

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.gbc.comp3071.hodgson_mackenzie.sensorlab.ui.theme.SensorLabTheme

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    private var temperatureSensor: Sensor? = null
    private var gyroscopeSensor: Sensor? = null

    // Compose-observable state
    private var temperature by mutableStateOf<Float?>(null)

    private var gyroX by mutableStateOf(0f)
    private var gyroY by mutableStateOf(0f)
    private var gyroZ by mutableStateOf(0f)

    private var temperatureAvailable by mutableStateOf(false)
    private var gyroscopeAvailable by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get Android SensorManager
        sensorManager =
            getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Discover available sensors
        temperatureSensor =
            sensorManager.getDefaultSensor(
                Sensor.TYPE_AMBIENT_TEMPERATURE
            )

        gyroscopeSensor =
            sensorManager.getDefaultSensor(
                Sensor.TYPE_GYROSCOPE
            )

        // Check sensor availability
        temperatureAvailable = temperatureSensor != null
        gyroscopeAvailable = gyroscopeSensor != null

        // Display Compose UI
        setContent {
            SensorLabTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    SensorScreen(
                        temperature = temperature,
                        temperatureAvailable = temperatureAvailable,
                        gyroX = gyroX,
                        gyroY = gyroY,
                        gyroZ = gyroZ,
                        gyroscopeAvailable = gyroscopeAvailable,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    // Register listeners when Activity becomes active
    override fun onResume() {
        super.onResume()

        temperatureSensor?.let { sensor ->
            sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

        gyroscopeSensor?.let { sensor ->
            sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    // Unregister listeners when Activity pauses
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    // Receive sensor readings
    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        when (event.sensor.type) {

            Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                val reading = event.values[0]

                if (reading.isFinite() && reading in -50f..100f) {
                    temperature = reading
                } else {
                    temperature = null
                }
            }

            Sensor.TYPE_GYROSCOPE -> {
                gyroX = event.values[0]
                gyroY = event.values[1]
                gyroZ = event.values[2]
            }
        }
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) {
        // Not required for this lab
    }
}

// Main sensor screen
@androidx.compose.runtime.Composable
fun SensorScreen(
    temperature: Float?,
    temperatureAvailable: Boolean,
    gyroX: Float,
    gyroY: Float,
    gyroZ: Float,
    gyroscopeAvailable: Boolean,
    modifier: Modifier = Modifier
) {
    Scaffold { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Android Sensor Monitor",
                style = MaterialTheme.typography.headlineMedium
            )

            TemperatureCard(
                temperature = temperature,
                available = temperatureAvailable
            )

            GyroscopeCard(
                x = gyroX,
                y = gyroY,
                z = gyroZ,
                available = gyroscopeAvailable
            )
        }
    }
}

// Temperature display
@androidx.compose.runtime.Composable
fun TemperatureCard(
    temperature: Float?,
    available: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Ambient Temperature",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (!available) {
                Text("Temperature sensor is not available.")
            } else if (temperature == null) {
                Text("Waiting for sensor reading...")
            } else {
                Text(
                    text = "%.1f °C".format(temperature),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}

// Gyroscope display
@androidx.compose.runtime.Composable
fun GyroscopeCard(
    x: Float,
    y: Float,
    z: Float,
    available: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Gyroscope",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (!available) {
                Text("Gyroscope is not available.")
            } else {
                Text("X: %.3f rad/s".format(x))
                Text("Y: %.3f rad/s".format(y))
                Text("Z: %.3f rad/s".format(z))
            }
        }
    }
}
