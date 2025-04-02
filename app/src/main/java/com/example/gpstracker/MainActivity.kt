package com.example.gpstracker

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.example.gpstracker.ui.theme.GPSTrackerTheme
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GPSTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Column {
                        MyApp(viewModel = LocationViewModel())
                    }
                }
            }
        }
    }
}


@Composable
fun MyApp(viewModel: LocationViewModel) {
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    LocationDisplay(locationUtils = locationUtils, viewModel, onLocationUpdate = { locationEntity ->
        FirebaseUtils(locationEntity)
    }, context = context)
}


@Composable
fun LocationDisplay(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    onLocationUpdate: (LocationEntity) -> FirebaseUtils,
    context: Context
) {
    val location = viewModel.location.value
    var firebaseUtils by remember { mutableStateOf<FirebaseUtils?>(null) }
    LaunchedEffect(location) { // Observe changes in location
        if (location != null) {
            // Convert the Location object to LocationEntity before passing
            val locationEntity = LocationEntity(
                latitude = location.latitude,
                longitude = location.longitude,
                altitude = location.altitude,
                accuracy = location.accuracy,
                speed = location.speed,
                bearing = location.bearing,
                timestamp = location.timestamp // Ensure this is a Long (milliseconds)
            )
            firebaseUtils = onLocationUpdate(locationEntity)
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions() ,
        onResult = { permissions ->
            if(permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true){
                // I HAVE ACCESS to location

                locationUtils.requestLocationUpdates(viewModel = viewModel)
            }else{
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as com.example.gpstracker.MainActivity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if(rationaleRequired){
                    Toast.makeText(context,
                        "Location Permission is required for this feature to work", Toast.LENGTH_LONG)
                        .show()
                }else{
                    Toast.makeText(context,
                        "Location Permission is required. Please enable it in the Android Settings",
                        Toast.LENGTH_LONG)
                        .show()
                }
            }
        })

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        if (location != null) {
            Text(
                "Latitude: ${location.latitude} \n" +
                        "Longitude: ${location.longitude} \n" +
                        "Altitude: ${location.altitude} \n" +
                        "Accuracy: ${location.accuracy} \n" +
                        "Speed: ${location.speed} \n" +
                        "Bearing: ${location.bearing} \n" +
                        "Time stamp: ${location.timestamp}"
            )

        } else {
            Text(text = "Location not available")
        }


        Button(onClick = {
            if (locationUtils.hasLocationPermission(context)) {
                // Permission already granted update the location
                locationUtils.requestLocationUpdates(viewModel)
            } else {
                // Request location permission
                requestPermissionLauncher.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }) {
            Text(text = "Get Location")

        }
        Button(onClick = {
            Toast.makeText(context, "Start recording location", Toast.LENGTH_SHORT).show()
            firebaseUtils?.uploadLocationData()
        }) {
            Text(text = "Start Recording Location")
        }
        Button(onClick = {
//            stopRecordingLocation(onLocationUpdate, context)
        }) {
            Text(text = "Stop Recording Location")

        }
    }
//    fun startRecordingLocation(firebaseUtils: FirebaseUtils, context: Context){
//        Toast.makeText(context, "Start recording location", Toast.LENGTH_SHORT).show()
//        firebaseUtils.uploadLocationData()
//    }
//
//    fun stopRecordingLocation(firebaseUtils: FirebaseUtils, context: Context){
//        Toast.makeText(context, "Stop recording location", Toast.LENGTH_SHORT).show()
//    }
}



